package com.proyecto.franquicia_reactiva.integration;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.infrastructure.repository.mongo.ReactiveMongoFranquiciaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@DataMongoTest
@TestPropertySource(properties = {
    "spring.mongodb.embedded.version=4.0.2"
})
public class FranquiciaRepositoryIntegrationTest {

    @Autowired
    private ReactiveMongoFranquiciaRepository repository;

    @Test
    void SaveAndRecoverFranquicia() {

        repository.deleteAll().block();
        franquicia franquicia = new franquicia(null, "Franquicia Test", List.of());

        Mono<franquicia> save = repository.save(franquicia);
        Mono<franquicia> result = save.flatMap(saved -> repository.findById(saved.getId()));

        StepVerifier.create(result)
                .expectNextMatches(f -> f.getName().equals("Franquicia Test"))
                .verifyComplete();
    }

    @Test
    void saveMultipleFranquiciasAndListThem() {
        
        repository.deleteAll().block();
        franquicia f1 = new franquicia(null, "Franquicia One", List.of());
        franquicia f2 = new franquicia(null, "Franquicia Two", List.of());
    
        repository.saveAll(List.of(f1, f2))
                .thenMany(repository.findAll())
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void deleteFranquiciaById() {

        repository.deleteAll().block();
        franquicia f = new franquicia(null, "Franquicia to delete", List.of());
    
        repository.save(f)
                .flatMap(saved -> repository.deleteById(saved.getId())
                    .then(repository.findById(saved.getId())))
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void updateNameFranquicia() {
        
        repository.deleteAll().block();
        franquicia f = new franquicia(null, "Old Name", List.of());
    
        repository.save(f)
                .flatMap(saved -> {
                    saved.setName("Updated Name");
                    return repository.save(saved);
                })
                .flatMap(updated -> repository.findById(updated.getId()))
                .as(StepVerifier::create)
                .expectNextMatches(fr -> fr.getName().equals("Updated Name"))
                .verifyComplete();
    }
}
