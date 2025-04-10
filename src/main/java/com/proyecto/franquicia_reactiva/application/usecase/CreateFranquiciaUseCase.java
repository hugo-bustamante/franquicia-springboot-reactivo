package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<franquicia> execute(franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }
}
