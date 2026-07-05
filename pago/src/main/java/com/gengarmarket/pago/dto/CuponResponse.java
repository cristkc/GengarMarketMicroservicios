package com.gengarmarket.pago.dto;

import lombok.Data;

@Data
public class CuponResponse {

    private Integer id;
    private String nombre;
    private String codigo;
    private Integer porcentaje;
}