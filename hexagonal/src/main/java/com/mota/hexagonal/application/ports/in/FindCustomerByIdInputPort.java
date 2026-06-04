package com.mota.hexagonal.application.ports.in;

import com.mota.hexagonal.application.core.domain.Customer;

public interface FindCustomerByIdInputPort {
    Customer find(String id);
}
