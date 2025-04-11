package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateNameFranquiciaUseCase {

    private final FranquiciaRepository repository;

    public Mono<Void> execute(String franquiciaId, String newName) {
        return repository.findById(franquiciaId)
            .flatMap(franquicia -> {
                franquicia.setName(newName);
                return repository.save(franquicia).then();
            });
    }
}

