package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.mockito.Mockito.*;

class UpdateNameProductUseCaseTest {

    private FranquiciaRepository repository;
    private UpdateNameProductUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        useCase = new UpdateNameProductUseCase(repository);
    }

    @Test
    void updateNameProduct() {
        String fId = "f1", sId = "s1", pId = "p1";
        String newName = "Update Product";

        Product product = new Product(pId, "Old Product", 10);
        Sucursal sucursal = new Sucursal(sId, "Sucursal", new ArrayList<>(List.of(product)));
        franquicia franquicia = new franquicia(fId, "Franquicia", List.of(sucursal));

        when(repository.findById(fId)).thenReturn(Mono.just(franquicia));
        when(repository.save(any())).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(fId, sId, pId, newName))
                .verifyComplete();

        verify(repository).save(argThat(f ->
                f.getSucursales().get(0).getProducts().get(0).getName().equals(newName)
        ));
    }
}
