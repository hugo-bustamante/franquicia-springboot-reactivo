package com.proyecto.franquicia_reactiva.application.usecase;

import java.util.UUID;

import com.proyecto.franquicia_reactiva.domain.model.Product;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import com.proyecto.franquicia_reactiva.infrastructure.exception.AlreadyExistsException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AddProductToSucursalUseCase {

    private final FranquiciaRepository repository;

    public Mono<Product> execute(String franquiciaId, String sucursalId, Product product) {
        return repository.findById(franquiciaId)
                .flatMap(franquicia -> {
                    // Validar si el producto con el mismo nombre ya existe en la sucursal
                    boolean exists = franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getId().equals(sucursalId))
                            .flatMap(sucursal -> sucursal.getProducts().stream())
                            .anyMatch(existingProduct -> existingProduct.getName().equalsIgnoreCase(product.getName()));
                    
                    if (exists) {
                        return Mono.error(new AlreadyExistsException("Producto con el mismo nombre ya existe en esta sucursal."));
                    }

                    if (product.getId() == null || product.getId().isBlank()) {
                        product.setId(UUID.randomUUID().toString());
                    }
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getId().equals(sucursalId))
                            .findFirst()
                            .ifPresent(sucursal -> sucursal.getProducts().add(product));
                    return repository.save(franquicia).thenReturn(product);
                });
    }
}
