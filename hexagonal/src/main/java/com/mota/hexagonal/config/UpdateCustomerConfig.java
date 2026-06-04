package com.mota.hexagonal.config;

import com.mota.hexagonal.adapters.out.FindAddressByZipCodeAdapter;
import com.mota.hexagonal.adapters.out.UpdateCustomerAdapter;
import com.mota.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import com.mota.hexagonal.application.core.usecase.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {
    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            UpdateCustomerAdapter updateCustomerAdapter
    ) {

        return new UpdateCustomerUseCase(findCustomerByIdUseCase, findAddressByZipCodeAdapter, updateCustomerAdapter);
    }
}
