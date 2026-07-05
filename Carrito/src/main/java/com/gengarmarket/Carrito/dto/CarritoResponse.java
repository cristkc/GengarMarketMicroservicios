package com.gengarmarket.Carrito.dto;

import java.util.List;

import com.gengarmarket.Carrito.model.Carrito;
import com.gengarmarket.Carrito.model.CarritoItem;

import lombok.*;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoResponse {

    private Long carritoId;
    private Long usuarioId;
    private List<CarritoItem> items;
    private Integer total;

    public CarritoResponse(Carrito carrito){
        this.carritoId = carrito.getId();
        this.usuarioId = carrito.getUsuarioId();
        this.items = carrito.getItems();
        this.total = calcularTotal(carrito);
    }

    private Integer calcularTotal(Carrito carrito) {
        return carrito.getItems().stream()
        .mapToInt(i -> i.getPrecioUnitario() * i.getCantidad())
        .sum();
    }
}
