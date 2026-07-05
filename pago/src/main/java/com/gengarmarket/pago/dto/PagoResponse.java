package com.gengarmarket.pago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponse {

    private String mensaje;
    private String estadoPedido;

    private Integer subtotal;
    private Integer descuento;
    private Integer totalFinal;
    private Integer porcentajeDescuento;
}   