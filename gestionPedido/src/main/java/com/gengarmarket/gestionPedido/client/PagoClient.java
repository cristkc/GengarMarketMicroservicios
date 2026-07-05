package com.gengarmarket.gestionPedido.client;

import com.gengarmarket.gestionPedido.dto.PagoResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Component
public class PagoClient {

    private final WebClient webClient;

    public PagoClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://pago")
                .build();
    }

    public Mono<PagoResponse> obtenerPago(Long usuarioId) {
        return webClient
                .get()
                .uri("/pagos/{usuarioId}", usuarioId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new NoSuchElementException("Pago no encontrado")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Servicio Pago no disponible")))
                .bodyToMono(PagoResponse.class);
    }
}