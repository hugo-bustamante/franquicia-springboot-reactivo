package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.mockito.Mockito.*;

class UpdateNameSucursalUseCaseTest {

    private FranquiciaRepository repository;
    private UpdateNameSucursalUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        useCase = new UpdateNameSucursalUseCase(repository);
    }

    @Test
    void updateNameSucursal() {
        String franquiciaId = "f1";
        String sucursalId = "s1";
        String newName = "Updated Sucursal";

        Sucursal sucursal = new Sucursal(sucursalId, "Old Sucursal", new ArrayList<>());
        franquicia franquicia = new franquicia(franquiciaId, "Franquicia", List.of(sucursal));

        when(repository.findById(franquiciaId)).thenReturn(Mono.just(franquicia));
        when(repository.save(any())).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(franquiciaId, sucursalId, newName))
                .verifyComplete();

        verify(repository).save(argThat(f -> f.getSucursales().get(0).getName().equals(newName)));
    }
}
