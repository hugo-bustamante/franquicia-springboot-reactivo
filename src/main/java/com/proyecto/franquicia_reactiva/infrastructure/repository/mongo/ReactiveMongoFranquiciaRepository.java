package com.proyecto.franquicia_reactiva.infrastructure.repository.mongo;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveMongoFranquiciaRepository extends ReactiveMongoRepository<franquicia, String> {
}