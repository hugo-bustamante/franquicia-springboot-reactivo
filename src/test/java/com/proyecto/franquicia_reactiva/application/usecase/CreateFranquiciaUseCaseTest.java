package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;

class CreateFranquiciaUseCaseTest {
    private final FranquiciaRepository repository = mock(FranquiciaRepository.class);
    private final CreateFranquiciaUseCase useCase = new CreateFranquiciaUseCase(repository);

    @Test
    void createFranquicia() {
        franquicia franquicia = new franquicia("1", "Central", null);
        when(repository.save(franquicia)).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute(franquicia))
                .expectNext(franquicia)
                .verifyComplete();

        verify(repository).save(franquicia);
    }
}
