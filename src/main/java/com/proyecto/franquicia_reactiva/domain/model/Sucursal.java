package com.proyecto.franquicia_reactiva.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    private String id;
    private String name;
    private List<Product> products;
}
