package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;

import static org.mockito.Mockito.*;

class UpdateNameFranquiciaUseCaseTest {

    private FranquiciaRepository repository;
    private UpdateNameFranquiciaUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        useCase = new UpdateNameFranquiciaUseCase(repository);
    }

    @Test
    void updateNameFranquicia() {
        String id = "f1";
        String newName = "New Franquicia";
        franquicia franquicia = new franquicia(id, "Old Name", List.of());

        when(repository.findById(id)).thenReturn(Mono.just(franquicia));
        when(repository.save(any())).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(id, newName))
                .verifyComplete();

        verify(repository).save(argThat(f -> f.getName().equals(newName)));
    }
}
