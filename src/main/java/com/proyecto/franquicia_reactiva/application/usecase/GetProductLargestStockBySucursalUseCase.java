package com.proyecto.franquicia_reactiva.application.usecase;

import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.domain.model.Product;
import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import java.util.List;

@RequiredArgsConstructor
public class GetProductLargestStockBySucursalUseCase {

    private final FranquiciaRepository repository;

    public Flux<ProductWithSucursalDTO> execute(String franquiciaId) {
        return repository.findById(franquiciaId)
                .flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales())
                        .flatMap(sucursal -> Flux.fromIterable(sucursal.getProducts())
                                .sort((a, b) -> Integer.compare(b.getStock(), a.getStock()))
                                .next()
                                .map(product -> new ProductWithSucursalDTO(sucursal.getId(), sucursal.getName(), product))));
    }

    public record ProductWithSucursalDTO(String sucursalId, String sucursalNombre, Product product) {}

}