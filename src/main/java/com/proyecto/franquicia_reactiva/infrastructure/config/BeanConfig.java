package com.proyecto.franquicia_reactiva.infrastructure.config;

import com.proyecto.franquicia_reactiva.application.usecase.*;
import com.proyecto.franquicia_reactiva.domain.repository.FranquiciaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateFranquiciaUseCase createFranquiciaUseCase(FranquiciaRepository repository) {
        return new CreateFranquiciaUseCase(repository);
    }

    @Bean
    public AddSucursalToFranquiciaUseCase addSucursalToFranquiciaUseCase(FranquiciaRepository repository) {
        return new AddSucursalToFranquiciaUseCase(repository);
    }

    @Bean
    public AddProductToSucursalUseCase addProductToSucursalUseCase(FranquiciaRepository repository) {
        return new AddProductToSucursalUseCase(repository);
    }

    @Bean
    public RemoveProductFromSucursalUseCase removeProductFromSucursalUseCase(FranquiciaRepository repository) {
        return new RemoveProductFromSucursalUseCase(repository);
    }

    @Bean
    public UpdateStockProductUseCase updateStockProductUseCase(FranquiciaRepository repository) {
        return new UpdateStockProductUseCase(repository);
    }

    @Bean
    public GetProductLargestStockBySucursalUseCase getProductoMayorStockPorSucursalUseCase(FranquiciaRepository repository) {
        return new GetProductLargestStockBySucursalUseCase(repository);
    }

    @Bean
    public UpdateNameFranquiciaUseCase updateNameFranquiciaUseCase(FranquiciaRepository repository) {
        return new UpdateNameFranquiciaUseCase(repository);
    }

    @Bean
    public UpdateNameSucursalUseCase updateNameSucursalUseCase(FranquiciaRepository repository) {
        return new UpdateNameSucursalUseCase(repository);
    }

    @Bean
    public UpdateNameProductUseCase updateNameProductUseCase(FranquiciaRepository repository) {
        return new UpdateNameProductUseCase(repository);
    }
}

