package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class AddSucursalToFranquiciaUseCaseTest {
    private final FranquiciaRepository repository = mock(FranquiciaRepository.class);
    private final AddSucursalToFranquiciaUseCase useCase = new AddSucursalToFranquiciaUseCase(repository);

    @Test
    void addSucursalToFranquicia() {
        franquicia franquicia = new franquicia("1", "Central", new ArrayList<>());
        Sucursal sucursal = new Sucursal(null, "Sucursal1", new ArrayList<>());
        when(repository.findById("1")).thenReturn(Mono.just(franquicia));
        when(repository.save(any())).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute("1", sucursal))
                .expectNextMatches(f -> f.getSucursales().contains(sucursal))
                .verifyComplete();
    }
}
