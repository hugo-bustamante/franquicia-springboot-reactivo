package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.*;

import static org.mockito.Mockito.*;

class AddProductToSucursalUseCaseTest {

    private FranquiciaRepository repository;
    private AddProductToSucursalUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        useCase = new AddProductToSucursalUseCase(repository);
    }

    @Test
    void AddProductToSucursal() {
        String franquiciaId = "f1";
        String sucursalId = "s1";

        Product product = new Product(null, "Product 1", 10);
        Sucursal sucursal = new Sucursal(sucursalId, "Sucursal", new ArrayList<>());
        franquicia franquicia = new franquicia(franquiciaId, "Franquicia", List.of(sucursal));

        when(repository.findById(franquiciaId)).thenReturn(Mono.just(franquicia));
        when(repository.save(any(franquicia.class))).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(franquiciaId, sucursalId, product))
                .expectNextMatches(p -> p.getId() != null && p.getName().equals("Product 1"))
                .verifyComplete();

        verify(repository).save(any());
    }
}