package com.proyecto.franquicia_reactiva.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "franquicias")
public class franquicia {
    @Id
    private String id;
    private String name;
    private List<Sucursal> sucursales;
}
