package com.mota.hexagonal.application.ports.in;

import com.mota.hexagonal.application.core.domain.Customer;

public interface UpdateCustomerInputPort {
    void update(Customer customer, String zipCode);
}
