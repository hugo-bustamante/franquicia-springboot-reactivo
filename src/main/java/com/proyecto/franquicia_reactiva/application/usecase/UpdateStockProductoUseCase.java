package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateStockProductoUseCase {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<Void> execute(String franquiciaId, String sucursalId, String productoId, int newStock) {
        return franquiciaRepository.findById(franquiciaId)
            .flatMap(franquicia -> {
                return franquicia.getSucursales().stream()
                    .filter(sucursal -> sucursal.getId().equals(sucursalId))
                    .findFirst()
                    .map(sucursal -> {
                        sucursal.getProductos().stream()
                            .filter(producto -> producto.getId().equals(productoId))
                            .findFirst()
                            .ifPresent(producto -> producto.setStock(newStock));
                        return franquiciaRepository.save(franquicia).then();
                    })
                    .orElse(Mono.empty());
            });
    }
}
