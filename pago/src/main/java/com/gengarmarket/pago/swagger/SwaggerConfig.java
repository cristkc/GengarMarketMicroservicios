package com.gengarmarket.pago.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI peliculasOpenAPI(){
        return new OpenAPI().info(new Info().title("API de Pago")
                        .description("API REST de proceso de pago")
                        .version("1.0")
                        .contact(new Contact().name("Cristhian llontop meier").email("cr.llontop@duocuc.cl")));

    }
}
