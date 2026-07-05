package com.TiendaZapatos.Despacho.Client;
import com.TiendaZapatos.Despacho.dto.gestionPedidoDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
@Component
public class PedidoClient {

    private final WebClient webClient;

    public PedidoClient(WebClient webClient){

        this.webClient = webClient;
    }

    public Mono<gestionPedidoDTO> obtenerPedido(Long id){

        return webClient
                .get()
                .uri("/pedido/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(
                                new NoSuchElementException("Pedido no encontrado")
                        ))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                new RuntimeException("Servicio gestionPedido no disponible")
                        ))
                .bodyToMono(gestionPedidoDTO.class);
    }
}