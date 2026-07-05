package com.gengarmarket.certificacion.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    private Long id;

    private Long usuarioId;

    private String nombreCliente;

    private String direccion;

    private Double totalFinal;

    private String fechaCompra;

    private String fechaEntrega;

    private String estado;
}