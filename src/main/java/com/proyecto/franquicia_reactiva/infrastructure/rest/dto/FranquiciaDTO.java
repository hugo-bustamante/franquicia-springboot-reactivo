package com.proyecto.franquicia_reactiva.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaDTO {
    private String id;
    private String name;
    private List<SucursalDTO> sucursales;
}
