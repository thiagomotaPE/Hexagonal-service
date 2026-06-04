package com.mota.hexagonal.application.core.usecase;

import com.mota.hexagonal.application.ports.in.DeleteCustomerByIdInputPort;
import com.mota.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import com.mota.hexagonal.application.ports.out.DeleteCustomerByIdOutputPort;
import com.mota.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.mota.hexagonal.application.ports.out.UpdateCustomerOutputPort;

public class DeleteCustomerByIdUseCase implements DeleteCustomerByIdInputPort {
    private final FindCustomerByIdInputPort findCustomerByIdInputPort;
    private final DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort;

    public DeleteCustomerByIdUseCase(
            FindCustomerByIdInputPort findCustomerByIdInputPort,
            DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort
    ) {
        this.findCustomerByIdInputPort = findCustomerByIdInputPort;
        this.deleteCustomerByIdOutputPort = deleteCustomerByIdOutputPort;
    }

    @Override
    public void delete(String id){
        findCustomerByIdInputPort.find(id);
        deleteCustomerByIdOutputPort.delete(id);
    };
}
