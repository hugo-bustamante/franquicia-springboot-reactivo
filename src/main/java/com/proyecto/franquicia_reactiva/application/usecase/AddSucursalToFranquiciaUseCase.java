package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;

import java.util.UUID;

import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddSucursalToFranquiciaUseCase {

    private final FranquiciaRepository repository;

    public Mono<franquicia> execute(String franquiciaId, Sucursal sucursal) {
        return repository.findById(franquiciaId)
                .map(franquicia -> {
                    if (sucursal.getId() == null || sucursal.getId().isBlank()) {
                        sucursal.setId(UUID.randomUUID().toString());
                    }
                    franquicia.getSucursales().add(sucursal);
                    return franquicia;
                })
                .flatMap(repository::save);
    }
}
