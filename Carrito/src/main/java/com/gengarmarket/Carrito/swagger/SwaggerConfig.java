package com.gengarmarket.Carrito.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI carritoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Carrito")
                        .description("API REST para gestionar el carrito de compras")
                        .version("1.0")
                        .contact(new Contact().name("Jose Ignacio Veliz").email("correo@ejemplo.com")));
    }
}