package com.proyecto.franquicia_reactiva.application.usecase;

import java.util.UUID;

import com.proyecto.franquicia_reactiva.domain.model.Product;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddProductToSucursalUseCase {

    private final FranquiciaRepository repository;

    public Mono<Product> execute(String franquiciaId, String sucursalId, Product product) {
        return repository.findById(franquiciaId)
                .flatMap(franquicia -> franquicia.getSucursales().stream()
                        .filter(sucursal -> sucursal.getId().equals(sucursalId))
                        .findFirst()
                        .map(sucursal -> {
                            if (product.getId() == null || product.getId().isBlank()) {
                                product.setId(UUID.randomUUID().toString());
                            }
                            sucursal.getProducts().add(product);
                            return repository.save(franquicia).thenReturn(product);
                        })
                        .orElse(Mono.empty())
                );
    }
}
