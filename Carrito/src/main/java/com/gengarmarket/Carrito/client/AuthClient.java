package com.gengarmarket.Carrito.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder,
                      @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder
                .baseUrl(authServiceUrl)
                .build();
    }

    public Mono<Boolean> validarUsuario(Long usuarioId) {
        return webClient.get()
                .uri("/auth/validar/{usuarioId}", usuarioId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new IllegalArgumentException("Usuario no encontrado")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Servicio auth no disponible")))
                .bodyToMono(Boolean.class);
    }
}