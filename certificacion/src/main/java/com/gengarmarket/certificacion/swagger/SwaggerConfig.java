package com.gengarmarket.certificacion.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI certificacionOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Certificación")
                        .description("API REST para la gestión de certificaciones de entrega")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Cristhian Llontop Meier")
                                .email("cr.llontop@duocuc.cl")));
    }
}