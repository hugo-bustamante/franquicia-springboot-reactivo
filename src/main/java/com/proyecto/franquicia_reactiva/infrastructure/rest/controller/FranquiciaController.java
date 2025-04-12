package com.proyecto.franquicia_reactiva.infrastructure.rest.controller;

import com.proyecto.franquicia_reactiva.application.usecase.*;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.FranquiciaDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.ProductDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.SucursalDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.mapper.FranquiciaMapper;
import com.proyecto.franquicia_reactiva.application.usecase.GetProductLargestStockBySucursalUseCase.ProductWithSucursalDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final CreateFranquiciaUseCase createFranquicia;
    private final AddSucursalToFranquiciaUseCase addSucursal;
    private final AddProductToSucursalUseCase addProduct;
    private final RemoveProductFromSucursalUseCase removeProducto;
    private final UpdateStockProductUseCase updateStock;
    private final GetProductLargestStockBySucursalUseCase getLargestStock;
    private final UpdateNameFranquiciaUseCase updateFranquiciaName;
    private final UpdateNameSucursalUseCase updateSucursalName;
    private final UpdateNameProductUseCase updateProductName;

    @PostMapping
    public Mono<FranquiciaDTO> crear(@RequestBody FranquiciaDTO dto) {
        return createFranquicia.execute(FranquiciaMapper.toModel(dto))
                .map(FranquiciaMapper::toDto);
    }

    @PostMapping("/{id}/sucursales")
    public Mono<FranquiciaDTO> addSucursal(@PathVariable String id, @RequestBody SucursalDTO sucursalDTO) {
        return addSucursal.execute(id, FranquiciaMapper.toModel(sucursalDTO))
                .map(FranquiciaMapper::toDto);
    }

    @PostMapping("/{id}/sucursales/{sucursalId}/products")
    public Mono<ProductDTO> addProduct(@PathVariable String id,
                                      @PathVariable String sucursalId,
                                      @RequestBody ProductDTO productDTO) {
        return addProduct.execute(id, sucursalId, FranquiciaMapper.toModel(productDTO))
                .map(FranquiciaMapper::toDto);
    }

    @DeleteMapping("/{id}/sucursales/{sucursalId}/products/{productId}")
    public Mono<Void> deleteProducto(@PathVariable String id,
                                       @PathVariable String sucursalId,
                                       @PathVariable String productId) {
        return removeProducto.execute(id, sucursalId, productId);
    }

    @PutMapping("/{id}/sucursales/{sucursalId}/products/{productId}/stock")
    public Mono<Void> updateStock(@PathVariable String id,
                                      @PathVariable String sucursalId,
                                      @PathVariable String productId,
                                      @RequestParam int newStock) {
        return updateStock.execute(id, sucursalId, productId, newStock);
    }

    @GetMapping("/{id}/products-largest-stock")
    public Flux<ProductWithSucursalDTO> LargestStock(@PathVariable String id) {
        return getLargestStock.execute(id);
    }

    @PutMapping("/{id}/name")
    public Mono<Void> updateNameFranquicia(@PathVariable String id,
                                                 @RequestParam String newName) {
        return updateFranquiciaName.execute(id, newName);
    }

    @PutMapping("/{id}/sucursales/{sucursalId}/name")
    public Mono<Void> updateNameSucursal(@PathVariable String id,
                                               @PathVariable String sucursalId,
                                               @RequestParam String newName) {
        return updateSucursalName.execute(id, sucursalId, newName);
    }

    @PutMapping("/{id}/sucursales/{sucursalId}/products/{productId}/name")
    public Mono<Void> updateNameProduct(@PathVariable String id,
                                               @PathVariable String sucursalId,
                                               @PathVariable String productId,
                                               @RequestParam String newName) {
        return updateProductName.execute(id, sucursalId, productId, newName);
    }
}