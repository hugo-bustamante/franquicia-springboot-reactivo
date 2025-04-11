package com.proyecto.franquicia_reactiva.infrastructure.repository.mongo;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepository {

    private final ReactiveMongoFranquiciaRepository repository;

    @Override
    public Mono<franquicia> save(franquicia franquicia) {
        return repository.save(franquicia);
    }

    @Override
    public Mono<franquicia> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<franquicia> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<franquicia> update(franquicia franquicia) {
        return repository.findById(franquicia.getId())
                .flatMap(existing -> repository.save(franquicia));
    }
}
