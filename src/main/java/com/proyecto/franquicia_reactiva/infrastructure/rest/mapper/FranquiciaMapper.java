package com.proyecto.franquicia_reactiva.infrastructure.rest.mapper;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.model.Product;
import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.FranquiciaDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.ProductDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.SucursalDTO;

import java.util.List;
import java.util.stream.Collectors;

public class FranquiciaMapper {

    public static franquicia toModel(FranquiciaDTO dto) {
        return new franquicia(
                dto.getId(),
                dto.getName(),
                dto.getSucursales() != null
                        ? dto.getSucursales().stream().map(FranquiciaMapper::toModel).collect(Collectors.toList())
                        : List.of()
        );
    }

    public static Sucursal toModel(SucursalDTO dto) {
        return new Sucursal(
                dto.getId(),
                dto.getName(),
                dto.getProducts() != null
                        ? dto.getProducts().stream().map(FranquiciaMapper::toModel).collect(Collectors.toList())
                        : List.of()
        );
    }

    public static Product toModel(ProductDTO dto) {
        return new Product(
                dto.getId(),
                dto.getName(),
                dto.getStock()
        );
    }

    public static FranquiciaDTO toDto(franquicia model) {
        return new FranquiciaDTO(
                model.getId(),
                model.getName(),
                model.getSucursales() != null
                        ? model.getSucursales().stream().map(FranquiciaMapper::toDto).collect(Collectors.toList())
                        : List.of()
        );
    }

    public static SucursalDTO toDto(Sucursal model) {
        return new SucursalDTO(
                model.getId(),
                model.getName(),
                model.getProducts() != null
                        ? model.getProducts().stream().map(FranquiciaMapper::toDto).collect(Collectors.toList())
                        : List.of()
        );
    }

    public static ProductDTO toDto(Product model) {
        return new ProductDTO(
                model.getId(),
                model.getName(),
                model.getStock()
        );
    }
}
