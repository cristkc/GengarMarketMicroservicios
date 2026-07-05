package com.gengarmarket.gestionPedido.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gestionPedidoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Pedido")
                        .description("API REST para la gestión de pedidos del sistema")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Cristhian Llontop Meier")
                                .email("cr.llontop@duocuc.cl")));
    }
}