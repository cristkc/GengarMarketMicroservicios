package com.TiendaZapatos.Despacho.swagger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI referenciasOpenAPI(){
        return new OpenAPI().info(new Info()
    .title("API DE REFERENCIAS")
    .description("API REST PARA LA GESTION DE LOS DESPACHOS")
    .version("1.0")
    .contact(new Contact()
.name("coreplay")
.email("elplayer1222@gmail.com")));
    }}
