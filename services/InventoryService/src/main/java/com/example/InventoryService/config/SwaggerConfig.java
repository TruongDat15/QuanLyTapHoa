package com.example.InventoryService.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("API Documentation Product")
                                .version("1.0.0")
                                .description("API documentation for the Demo Application")
                );

    }


    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("my-api")
                .packagesToScan("com.example.InventoryService.controller") // chỉ quét package controller
                .build();
    }
}
