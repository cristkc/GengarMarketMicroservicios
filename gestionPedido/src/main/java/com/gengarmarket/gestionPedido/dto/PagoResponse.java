package com.gengarmarket.gestionPedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponse {

    private Long id;

    private Long usuarioId;

    private String nombreCliente;

    private String direccion;

    private Double totalFinal;

    private String estadoPedido;

    private String mensaje;
}