# Hexagonal-service

Projeto em Spring Boot organizado com Arquitetura Hexagonal para cadastro, consulta, atualização e exclusão de clientes, com integração com MongoDB, Kafka e um serviço externo de endereço consumido via WireMock.

## O que o projeto faz

Este projeto expõe uma API REST para gerenciar clientes.

Ao cadastrar ou atualizar um cliente:

1. A aplicação recebe os dados via endpoint HTTP.
2. Busca o endereço a partir do `zipCode` em um serviço externo.
3. Salva o cliente no MongoDB.
4. Publica o CPF no Kafka para validação.
5. Consome a resposta da validação do CPF em outro tópico e atualiza o cliente com o resultado.

Também é possível consultar um cliente por ID e excluí-lo.

## Arquitetura utilizada

O projeto segue Arquitetura Hexagonal.

```text
Web adapter                                                             Persistence adapter
                          <=> Input ports == CORE == Output ports <=>
External system adapter                                                 External system adapter
```

### Como está organizado

- `adapters/in`
  - Controller REST
  - Consumer Kafka
  - Mappers de entrada
- `application/core`
  - Entidades de domínio
  - Casos de uso
- `application/ports/in`
  - Portas de entrada
- `application/ports/out`
  - Portas de saída
- `adapters/out`
  - MongoDB repository
  - Cliente Feign para endereço
  - Producer Kafka
  - Adapters de persistência e integração
- `config`
  - Beans que conectam os use cases aos adapters

### Fluxo principal

**Cadastro de cliente**
- `POST /api/v1/customers`
- O `CustomerController` chama o `InsertCustomerUseCase`
- O use case busca o endereço via porta de saída
- O cliente é salvo no MongoDB
- O CPF é enviado para validação no Kafka

**Atualização de cliente**
- `PUT /api/v1/customers/{id}`
- Valida se o cliente existe
- Busca o endereço novamente pelo `zipCode`
- Atualiza o registro no MongoDB

**Consulta de cliente**
- `GET /api/v1/customers/{id}`

**Exclusão de cliente**
- `DELETE /api/v1/customers/{id}`

**Consumo Kafka**
- O consumer escuta o tópico `tp-cpf-validated`
- Converte a mensagem em domínio
- Atualiza o cliente com o resultado da validação

## Tecnologias e ferramentas utilizadas

- Java 17
- Spring Boot
- Spring WebMVC
- Spring Data MongoDB
- Spring Kafka
- Spring Cloud OpenFeign
- MapStruct
- Lombok
- ArchUnit
- Maven
- Docker
- MongoDB
- Kafka
- Zookeeper
- Kafdrop
- Mongo Express
- WireMock

### Ferramentas de desenvolvimento que utilizei
- Spring Initializr
- Maven Repository
- WireMock
- Offset Explorer
- Plugin Kafkalytic no IntelliJ

## Pré-requisitos

- Java 17
- Maven
- Docker e Docker Compose
- WireMock executando localmente na porta `8082`
- MongoDB e Kafka rodando localmente
- `mongosh` para acessar o banco

## Como rodar o projeto

### 1) Subir o WireMock

Coloque o JAR do WireMock em uma pasta e execute o comando dentro dela:

```bash
java -jar .\wiremock-standalone-4.0.0-beta.36.jar --port 8082
```

O projeto usa a URL:

```text
http://localhost:8082/addresses
```

Então o WireMock precisa responder rotas de endereço compatíveis com esse endpoint.

- substitua a pasta mappings do WireMock pela que esta no repositorio e rode novamente o comando acima

### 2) Subir as dependências com Docker

Na pasta onde está o `docker-compose.yml`:

```bash
docker compose up -d
```

Esse compose sobe:

- Kafka
- Zookeeper
- Kafdrop
- MongoDB
- Mongo Express

### 3) Rodar a aplicação

Na raiz do projeto:

```bash
./mvnw spring-boot:run
```

No Windows, se preferir:

```bash
mvn spring-boot:run
```

A aplicação sobe na porta `8081`.

## Configurações importantes

### Kafka
- Broker: `localhost:9092`
- Tópico de envio do CPF: `tp-cpf-validation`
- Tópico de consumo da validação: `tp-cpf-validated`

### MongoDB
A aplicação está configurada para usar:

- Host: `localhost`
- Porta: `27017`
- Usuário: `root`
- Senha: `example`
- Banco: `hexagonal`

## Como testar

### 1) Testar a API

#### Criar cliente
```bash
curl -X POST http://localhost:8081/api/v1/customers ^
  -H "Content-Type: application/json" ^
  -d "{"name":"João Silva","cpf":"12345678900","zipCode":"38400000"}"
```

#### Buscar cliente por ID
```bash
curl http://localhost:8081/api/v1/customers/{id}
```

#### Atualizar cliente
```bash
curl -X PUT http://localhost:8081/api/v1/customers/{id} ^
  -H "Content-Type: application/json" ^
  -d "{"name":"João Silva","cpf":"12345678900","zipCode":"38400001"}"
```

#### Remover cliente
```bash
curl -X DELETE http://localhost:8081/api/v1/customers/{id}
```

### 2) Conferir o MongoDB

Entrar no container do Mongo:

```bash
docker exec -it <container_id_ou_nome> /bin/bash
```

Acessar o terminal do Mongo:

```bash
mongosh -u root -p
```

Comandos úteis:

```bash
use hexagonal
show collections
db.customers.find()
```

### 3) Conferir Kafka

Você pode usar o Kafdrop em:

```text
http://localhost:9000
```

Também é possível usar o Offset Explorer ou o plugin Kafkalytic no IntelliJ.

### 4) Rodar os testes

```bash
./mvnw test
```

O projeto possui testes de arquitetura com ArchUnit para validar:
- organização por camadas
- convenções de nomes
- isolamento dos packages

## Estrutura resumida

```text
src/main/java/com/mota/hexagonal
├── adapters
│   ├── in
│   │   ├── controller
│   │   └── consumer
│   └── out
│       ├── client
│       └── repository
├── application
│   ├── core
│   │   ├── domain
│   │   └── usecase
│   └── ports
│       ├── in
│       └── out
└── config
```

## Observações

- O projeto depende do WireMock para simular o serviço externo de consulta de endereço.
- O Kafka precisa estar disponível antes de publicar ou consumir mensagens.
- O MongoDB precisa estar disponível para persistência dos clientes.
- As integrações externas estão desacopladas do core por portas e adapters, seguindo a proposta da Arquitetura Hexagonal.
