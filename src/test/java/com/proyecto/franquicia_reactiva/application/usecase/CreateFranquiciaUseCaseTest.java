package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import com.proyecto.franquicia_reactiva.infrastructure.exception.AlreadyExistsException;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;

class CreateFranquiciaUseCaseTest {
    private final FranquiciaRepository repository = mock(FranquiciaRepository.class);
    private final CreateFranquiciaUseCase useCase = new CreateFranquiciaUseCase(repository);

    @Test
    void createFranquicia() {
        franquicia franquicia = new franquicia("1", "Central", null);
        
        // Configuración del mock para que findAll() devuelva un Flux vacío.
        when(repository.findAll()).thenReturn(Flux.empty());
        when(repository.save(franquicia)).thenReturn(Mono.just(franquicia));

        // Verificamos que el flujo se complete correctamente y que se devuelva la franquicia.
        StepVerifier.create(useCase.execute(franquicia))
                .expectNext(franquicia) // Verifica que la franquicia se devuelve correctamente
                .verifyComplete();

        verify(repository).save(franquicia); // Verifica que se haya llamado al método save
    }

    @Test
    void createFranquicia_whenFranquiciaExists() {
        franquicia franquicia = new franquicia("1", "Central", null);

        // Configuración del mock para que findAll() devuelva una franquicia ya existente
        franquicia existingFranquicia = new franquicia("1", "Central", null);
        when(repository.findAll()).thenReturn(Flux.just(existingFranquicia));

        // Ejecutamos el test
        StepVerifier.create(useCase.execute(franquicia))
                .expectErrorMatches(ex -> ex instanceof AlreadyExistsException)
                .verify();
    }
}