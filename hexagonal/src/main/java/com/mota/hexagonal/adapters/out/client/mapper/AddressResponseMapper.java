package com.mota.hexagonal.adapters.out.client.mapper;

import com.mota.hexagonal.adapters.out.client.response.AddressResponse;
import com.mota.hexagonal.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper {
    Address toAddress(AddressResponse addressResponse);
}
