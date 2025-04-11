package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateStockProductUseCase {

    private final FranquiciaRepository repository;

    public Mono<Void> execute(String franquiciaId, String sucursalId, String productId, int newStock) {
        return repository.findById(franquiciaId)
                .flatMap(franquicia -> franquicia.getSucursales().stream()
                        .filter(sucursal -> sucursal.getId().equals(sucursalId))
                        .findFirst()
                        .map(sucursal -> {
                            sucursal.getProducts().stream()
                                    .filter(p -> p.getId().equals(productId))
                                    .findFirst()
                                    .ifPresent(p -> p.setStock(newStock));
                            return repository.save(franquicia).then();
                        })
                        .orElse(Mono.empty()));
    }
}
