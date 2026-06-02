package com.mota.hexagonal.application.ports.in;

import com.mota.hexagonal.application.core.domain.Customer;

public interface InsertCustomerInputPort {
    void insert(Customer customer, String zipCode);
}
