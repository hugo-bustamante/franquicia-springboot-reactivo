package com.proyecto.franquicia_reactiva.infrastructure.rest.controller;

import com.proyecto.franquicia_reactiva.application.usecase.*;
import com.proyecto.franquicia_reactiva.domain.model.Product;
import com.proyecto.franquicia_reactiva.domain.model.Sucursal;
import com.proyecto.franquicia_reactiva.domain.model.franquicia;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.FranquiciaDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.ProductDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.dto.SucursalDTO;
import com.proyecto.franquicia_reactiva.infrastructure.rest.mapper.FranquiciaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranquiciaController.class)
class FranquiciaControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean private CreateFranquiciaUseCase createFranquicia;
    @MockBean private AddSucursalToFranquiciaUseCase addSucursal;
    @MockBean private AddProductToSucursalUseCase addProduct;
    @MockBean private RemoveProductFromSucursalUseCase removeProduct;
    @MockBean private UpdateStockProductUseCase updateStock;
    @MockBean private GetProductLargestStockBySucursalUseCase getLargestStock;
    @MockBean private UpdateNameFranquiciaUseCase updateFranquiciaName;
    @MockBean private UpdateNameSucursalUseCase updateSucursalName;
    @MockBean private UpdateNameProductUseCase updateProductName;

    private FranquiciaDTO franquiciaDTO;

    @BeforeEach
    void setup() {
        franquiciaDTO = new FranquiciaDTO("f1", "Franquicia Central", List.of());
    }

    @Test
    void addFranquicia() {
        when(createFranquicia.execute(any())).thenReturn(Mono.just(FranquiciaMapper.toModel(franquiciaDTO)));

        webClient.post().uri("/api/franquicias")
                .bodyValue(franquiciaDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("f1");
    }

    @Test
    void addSucursal() {
        SucursalDTO sucursal = new SucursalDTO("s1", "Sucursal Norte", List.of());
        franquicia f = new franquicia("f1", "Franquicia Central", List.of(FranquiciaMapper.toModel(sucursal)));
        when(addSucursal.execute(eq("f1"), any())).thenReturn(Mono.just(f));

        webClient.post().uri("/api/franquicias/f1/sucursales")
                .bodyValue(sucursal)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("f1");
    }

    @Test
    void addProduct() {
        ProductDTO product = new ProductDTO(null, "Product X", 10);
        Product conId = new Product("p1", "Product X", 10);
        when(addProduct.execute(eq("f1"), eq("s1"), any())).thenReturn(Mono.just(conId));

        webClient.post().uri("/api/franquicias/f1/sucursales/s1/products")
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("p1")
                .jsonPath("$.name").isEqualTo("Product X")
                .jsonPath("$.stock").isEqualTo(10);
    }

    @Test
    void deleteProduct() {
        when(removeProduct.execute(any(), any(), any())).thenReturn(Mono.empty());

        webClient.delete().uri("/api/franquicias/f1/sucursales/s1/products/p1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateStock() {
        when(updateStock.execute("f1", "s1", "p1", 20)).thenReturn(Mono.empty());

        webClient.put().uri("/api/franquicias/f1/sucursales/s1/products/p1/stock?newStock=20")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateNameFranquicia() {
        when(updateFranquiciaName.execute("f1", "Nascar")).thenReturn(Mono.empty());

        webClient.put().uri("/api/franquicias/f1/name?newName=Nascar")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateNameSucursal() {
        when(updateSucursalName.execute("f1", "s1", "Sucursal Multiplaza")).thenReturn(Mono.empty());

        webClient.put().uri("/api/franquicias/f1/sucursales/s1/name?newName=Sucursal Multiplaza")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateNameProduct() {
        when(updateProductName.execute("f1", "s1", "p1", "Product Onion")).thenReturn(Mono.empty());

        webClient.put().uri("/api/franquicias/f1/sucursales/s1/products/p1/name?newName=Product Onion")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getProductLargestStockBySucursal() {
        Product product = new Product("p1", "Largest Stock", 99);
        GetProductLargestStockBySucursalUseCase.ProductWithSucursalDTO dto =
                new GetProductLargestStockBySucursalUseCase.ProductWithSucursalDTO("s1", "Sucursal 1", product);

        when(getLargestStock.execute("f1")).thenReturn(Flux.just(dto));

        webClient.get().uri("/api/franquicias/f1/products-largest-stock")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].sucursalId").isEqualTo("s1")
                .jsonPath("$[0].product.name").isEqualTo("Largest Stock");
    }
}

