package com.sbt.lazy.service.mapper;

import com.sbt.lazy.domain.*;
import com.sbt.lazy.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {


    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "removeHistory", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
