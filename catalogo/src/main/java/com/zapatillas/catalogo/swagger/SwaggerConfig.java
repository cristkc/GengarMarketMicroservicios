package com.zapatillas.catalogo.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI catalogoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DEBO ESTUDIAR PARA EL EXAMEN ")
                        .description("API REST de catalogo de zapatillas")
                        .version("1.0")
                        .contact(new Contact().name("Felipe Antonio Veliz")  .email("fel.veliza@duocuc.c")));
    }

}