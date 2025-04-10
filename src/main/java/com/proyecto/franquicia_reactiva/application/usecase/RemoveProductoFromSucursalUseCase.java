package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RemoveProductoFromSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    public Mono<Void> execute(String franquiciaId, String sucursalId, String productoId) {
        return franquiciaRepository.findById(franquiciaId)
            .flatMap(franquicia -> {
                return franquicia.getSucursales().stream()
                    .filter(sucursal -> sucursal.getId().equals(sucursalId))
                    .findFirst()
                    .map(sucursal -> {
                        sucursal.getProductos().removeIf(producto -> producto.getId().equals(productoId));
                        return franquiciaRepository.save(franquicia).then();
                    })
                    .orElse(Mono.empty());
            });
    }
}
