package com.mota.hexagonal.application.ports.out;

import com.mota.hexagonal.application.core.domain.Customer;

public interface InsertCostumerOutputPort {
    void insert(Customer customer);
}
