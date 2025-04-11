package com.proyecto.franquicia_reactiva.domain.repository;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaRepository {
    Mono<franquicia> save(franquicia franquicia);
    Mono<franquicia> findById(String id);
    Flux<franquicia> findAll();
    Mono<Void> deleteById(String id);
    Mono<franquicia> update(franquicia franquicia);
}

