package com.mota.hexagonal.application.core.usecase;

import com.mota.hexagonal.application.core.domain.Customer;
import com.mota.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.mota.hexagonal.application.ports.out.InsertCostumerOutputPort;

public class InsertCustomerUseCase {
    private final FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort;
    private final InsertCostumerOutputPort insertCostumerOutputPort;

    public InsertCustomerUseCase(
            FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
            InsertCostumerOutputPort insertCostumerOutputPort
    ) {
        this.findAddressByZipCodeOutputPort = findAddressByZipCodeOutputPort;
        this.insertCostumerOutputPort = insertCostumerOutputPort;
    }

    public void insert(Customer customer, String zipCode) {
        var address = findAddressByZipCodeOutputPort.find(zipCode);
        customer.setAddress(address);

        insertCostumerOutputPort.insert(customer);
    }
}
