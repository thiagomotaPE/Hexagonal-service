package com.mota.hexagonal.application.ports.out;

import com.mota.hexagonal.application.core.domain.Address;

public interface FindAddressByZipCodeOutputPort {
    Address find(String zipCode);
}
