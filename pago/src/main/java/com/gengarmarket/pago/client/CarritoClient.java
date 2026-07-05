package com.gengarmarket.pago.client;

import com.gengarmarket.pago.dto.CarritoResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
public class CarritoClient {

    private final WebClient webClient;

        public CarritoClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://carrito")
                .build();
        }

    public Mono<CarritoResponse> obtenerCarrito(Long usuarioId) {
        return webClient.get()
                .uri("/carrito/{usuarioId}", usuarioId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new NoSuchElementException("Carrito no encontrado")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error en microservicio Carrito")))
                .bodyToMono(CarritoResponse.class);
    }
}