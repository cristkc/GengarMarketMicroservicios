package com.gengarmarket.Carrito.dto;

import lombok.Data;

@Data
public class ZapatillaResponse {
    private Integer sku;
    private String nombre;
    private String marca;
    private String modelo;
    private String talla;
    private Integer precio;
    private Integer stock;

}
