package com.reportes.reportess.Client;
import com.reportes.reportess.dto.PagoResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import java.util.List;
import java.util.NoSuchElementException;
@Component
public class PagoClient {
    private final WebClient webClient;

    public PagoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<PagoResponse>> obtenerPagos(){

        return webClient.get()
                .uri("/pagos")
                .retrieve()

                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(
                                new NoSuchElementException(
                                        "No se han encontrado pagos"
                                )
                        ))

                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                new RuntimeException(
                                        "Error en microservicio Pago"
                                )
                        ))

                .bodyToFlux(PagoResponse.class)
                .collectList();
    }

}
