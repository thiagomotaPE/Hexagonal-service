package com.mota.hexagonal.config;

import com.mota.hexagonal.adapters.out.FindAddressByZipCodeAdapter;
import com.mota.hexagonal.adapters.out.InsertCostumerAdapter;
import com.mota.hexagonal.application.core.usecase.InsertCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertCustomerConfig {
    @Bean
    public InsertCustomerUseCase insertCustomerUseCase(
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            InsertCostumerAdapter insertCostumerAdapter
    ) {

        return new InsertCustomerUseCase(findAddressByZipCodeAdapter, insertCostumerAdapter);
    }
}
