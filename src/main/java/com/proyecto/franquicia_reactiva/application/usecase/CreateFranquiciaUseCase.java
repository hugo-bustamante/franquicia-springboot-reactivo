package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import com.proyecto.franquicia_reactiva.infrastructure.exception.AlreadyExistsException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateFranquiciaUseCase {

    private final FranquiciaRepository repository;

    public Mono<franquicia> execute(franquicia franquicia) {
        return repository.findAll()
                .filter(existingFranquicia -> existingFranquicia.getName().equalsIgnoreCase(franquicia.getName()))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new AlreadyExistsException("Ya existe una franquicia con el mismo nombre."));
                    }
                    return repository.save(franquicia);
                });
    }
}
