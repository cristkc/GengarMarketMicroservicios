        package com.gengarmarket.Carrito.client;

        import com.gengarmarket.Carrito.dto.ZapatillaResponse;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.HttpStatusCode;
        import org.springframework.stereotype.Component;
        import org.springframework.web.reactive.function.client.WebClient;
        import reactor.core.publisher.Mono;

        import java.util.NoSuchElementException;

        @Component
        public class CatalogoClient {

        private final WebClient webClient;

        public CatalogoClient(WebClient.Builder webClientBuilder,
                                @Value("${catalogo.service.url}") String catalogoServiceUrl) {
                this.webClient = webClientBuilder
                        .baseUrl(catalogoServiceUrl)
                        .build();
        }

        public Mono<ZapatillaResponse> obtenerPorSku(Integer sku) {
                return webClient.get()
                        .uri("/catalogo/buscarSku/{sku}", sku)
                        .retrieve()
                        .onStatus(status -> status.value() == 404,
                                response -> Mono.error(new NoSuchElementException("Producto con sku " + sku + " no encontrado")))
                        .onStatus(HttpStatusCode::is5xxServerError,
                                response -> Mono.error(new RuntimeException("Servicio catálogo no disponible")))
                        .bodyToMono(ZapatillaResponse.class);
        }
        }