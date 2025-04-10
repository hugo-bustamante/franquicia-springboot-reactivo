package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.model.Producto;
import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@AllArgsConstructor
public class GetProductoMayorStockPorSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    public Flux<ProductoConSucursalDTO> execute(String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
            .flatMapMany(franquicia -> {
                List<Sucursal> sucursales = franquicia.getSucursales();
                return Flux.fromIterable(sucursales)
                    .flatMap(sucursal -> Flux.fromIterable(sucursal.getProductos())
                        .sort((a, b) -> Integer.compare(b.getStock(), a.getStock()))
                        .next()
                        .map(producto -> new ProductoConSucursalDTO(sucursal.getId(), sucursal.getNombre(), producto)));
            });
    }

    public record ProductoConSucursalDTO(String sucursalId, String sucursalNombre, Producto producto) {}
}