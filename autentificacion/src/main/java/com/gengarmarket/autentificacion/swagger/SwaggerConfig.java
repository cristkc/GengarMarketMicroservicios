package com.gengarmarket.autentificacion.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI autentificacionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autentificación")
                        .description("API REST para la gestión y autenticación de usuarios")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Felipe Antonio Veliz")
                                .email("fel.veliza@duocuc.cl")));
    }

}