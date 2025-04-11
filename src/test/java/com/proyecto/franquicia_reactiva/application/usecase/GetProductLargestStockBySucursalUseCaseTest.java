package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.mockito.Mockito.*;

class GetProductLargestStockBySucursalUseCaseTest {

    private FranquiciaRepository repository;
    private GetProductLargestStockBySucursalUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        useCase = new GetProductLargestStockBySucursalUseCase(repository);
    }

    @Test
    void returnProductLargestStockBySucursalUseCase() {
        String fId = "f1";
        Product p1 = new Product("p1", "A", 5);
        Product p2 = new Product("p2", "B", 20);
        Sucursal sucursal = new Sucursal("s1", "Sucursal One", List.of(p1, p2));
        franquicia franquicia = new franquicia(fId, "Franquicia", List.of(sucursal));

        when(repository.findById(fId)).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(fId))
                .expectNextMatches(dto -> dto.product().getStock() == 20 &&
                        dto.sucursalId().equals("s1") &&
                        dto.product().getName().equals("B"))
                .verifyComplete();
    }
}
