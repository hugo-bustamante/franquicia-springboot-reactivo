package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddSucursalToFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<franquicia> execute(String franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.findById(franquiciaId)
            .map(f -> {
                f.getSucursales().add(sucursal);
                return f;
            })
            .flatMap(franquiciaRepository::save);
    }
}
