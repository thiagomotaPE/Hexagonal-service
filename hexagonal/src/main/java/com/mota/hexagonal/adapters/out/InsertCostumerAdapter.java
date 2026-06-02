package com.mota.hexagonal.adapters.out;

import com.mota.hexagonal.adapters.out.repository.CustomerRepository;
import com.mota.hexagonal.adapters.out.repository.mapper.CustomerEntityMapper;
import com.mota.hexagonal.application.core.domain.Customer;
import com.mota.hexagonal.application.ports.out.InsertCostumerOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertCostumerAdapter implements InsertCostumerOutputPort {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    @Override
    public void insert(Customer customer) {
        var customerEntity = customerEntityMapper.toCustomerEntity(customer);
        customerRepository.save(customerEntity);
    }
}
