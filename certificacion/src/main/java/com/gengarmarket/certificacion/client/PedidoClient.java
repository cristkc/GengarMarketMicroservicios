package com.gengarmarket.certificacion.client;

import com.gengarmarket.certificacion.dto.PedidoResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
public class PedidoClient {

    private final WebClient webClient;

    public PedidoClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://gestionpedido")
                .build();
    }

    public Mono<PedidoResponse> obtenerPedido(Long id) {
        return webClient.get()
                .uri("/pedido/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new NoSuchElementException("Pedido no encontrado")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error en microservicio Pedido")))
                .bodyToMono(PedidoResponse.class);
    }
}