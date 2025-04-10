package com.proyecto.franquicia_reactiva.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private String id;
    private String nombre;
    private int stock;
}
