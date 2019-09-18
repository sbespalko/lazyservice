package com.sbt.lazy.service.mapper;

import com.sbt.lazy.domain.*;
import com.sbt.lazy.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "client.id", target = "clientId")
    ProductDTO toDto(Product product);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "removeHistory", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
