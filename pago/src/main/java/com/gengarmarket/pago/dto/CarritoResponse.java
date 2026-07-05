package com.gengarmarket.pago.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarritoResponse{

    private Long carritoId;
    private Long usuarioId;
    private List<CarritoItemDTO> items;
    private Integer total;
}