package com.mota.hexagonal.config;


import com.mota.hexagonal.adapters.out.DeleteCustomerAdapter;
import com.mota.hexagonal.application.core.usecase.DeleteCustomerByIdUseCase;
import com.mota.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCustomerByIdConfig {
    @Bean
    public DeleteCustomerByIdUseCase deleteCustomerByIdUseCase(
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            DeleteCustomerAdapter deleteCustomerAdapter
    ) {

        return new DeleteCustomerByIdUseCase(findCustomerByIdUseCase, deleteCustomerAdapter);
    }
}
