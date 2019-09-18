package com.sbt.lazy.service.mapper;

import com.sbt.lazy.domain.*;
import com.sbt.lazy.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "client.id", target = "clientId")
    AddressDTO toDto(Address address);

    @Mapping(source = "clientId", target = "client")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
