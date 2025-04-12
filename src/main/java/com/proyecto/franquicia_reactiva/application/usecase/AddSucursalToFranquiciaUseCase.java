package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;

import java.util.UUID;

import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import com.proyecto.franquicia_reactiva.infrastructure.exception.AlreadyExistsException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddSucursalToFranquiciaUseCase {

    private final FranquiciaRepository repository;

    public Mono<franquicia> execute(String franquiciaId, Sucursal sucursal) {
        return repository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    // Validar que no exista una sucursal con el mismo nombre
                    boolean exists = franquicia.getSucursales().stream()
                            .anyMatch(s -> s.getName().equalsIgnoreCase(sucursal.getName()));
                    
                    if (exists) {
                        return Mono.error(new AlreadyExistsException("Sucursal con el mismo nombre, ya existe."));
                    }

                    if (sucursal.getId() == null || sucursal.getId().isBlank()) {
                        sucursal.setId(UUID.randomUUID().toString());
                    }
                    franquicia.getSucursales().add(sucursal);
                    return repository.save(franquicia);
                });
    }
}
