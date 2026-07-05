package com.gengarmarket.pago.dto;

import lombok.Data;

@Data
public class CarritoItemDTO {

    private Integer sku;
    private String nombre;
    private String marca;
    private Integer precioUnitario;
    private Integer cantidad;
}