package com.mota.hexagonal.application.ports.out;

import com.mota.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerOutputPort {
    void update(Customer customer);
}
