package com.TiendaZapatillas.Referencias.Client;

import com.TiendaZapatillas.Referencias.dto.UsuarioDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ClienteAutenticacion {

    private final WebClient webClient;

    public ClienteAutenticacion(WebClient webClient) {
        this.webClient = webClient;
    }

  public Boolean obtenerUsuario(Integer id) {

    UsuarioDTO usuario = webClient
            .get()
            .uri("/auth/validar/{id}", id)
            .retrieve()
            .bodyToMono(UsuarioDTO.class)
            .block();
             
              if (usuario.isExiste() == false) {
        throw new RuntimeException("No se pudo obtener el usuario");
    }

    return usuario.isExiste()== true;
}
  }