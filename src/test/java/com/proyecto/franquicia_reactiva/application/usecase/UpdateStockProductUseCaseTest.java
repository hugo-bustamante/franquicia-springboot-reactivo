package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.mockito.Mockito.*;

class UpdateStockProductUseCaseTest {
    private final FranquiciaRepository repository = mock(FranquiciaRepository.class);
    private final UpdateStockProductUseCase useCase = new UpdateStockProductUseCase(repository);

    @Test
    void updateStock() {
        Product product = new Product("prod1", "Product1", 3);
        Sucursal sucursal = new Sucursal("suc1", "Sucursal1", new ArrayList<>(List.of(product)));
        franquicia franquicia = new franquicia("1", "Central", new ArrayList<>(List.of(sucursal)));

        when(repository.findById("1")).thenReturn(Mono.just(franquicia));
        when(repository.save(franquicia)).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute("1", "suc1", "prod1", 99))
                .verifyComplete();

        assert product.getStock() == 99;
        verify(repository).save(franquicia);
    }
}
