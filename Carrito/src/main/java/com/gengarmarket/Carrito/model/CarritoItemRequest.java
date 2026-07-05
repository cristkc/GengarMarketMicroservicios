package com.gengarmarket.Carrito.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoItemRequest {

    @NotNull(message = "El sku es obligatorio")
    private Integer sku;

    @NotNull(message = "la cantidad es obligatoria")
    @Min (value = 1, message = "la cantidad debe ser mayor a 0")
    private Integer cantidad;
}
