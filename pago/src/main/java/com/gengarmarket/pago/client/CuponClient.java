package com.gengarmarket.pago.client;

import com.gengarmarket.pago.dto.CuponResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
public class CuponClient {

    private final WebClient webClient;

        public CuponClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://cupones")
                .build();
        }

    public Mono<CuponResponse> obtenerCupon(String codigo) {
        return webClient.get()
                .uri("/cupones/codigo/{codigo}", codigo)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new NoSuchElementException("Cupón no encontrado")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error en microservicio Cupón")))
                .bodyToMono(CuponResponse.class);
    }
}