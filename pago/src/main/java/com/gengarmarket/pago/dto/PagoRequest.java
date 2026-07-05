package com.gengarmarket.pago.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PagoRequest {

    private Long usuarioId;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreCliente;

    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "Número de tarjeta obligatorio")
    @Size(min = 16, max = 16, message = "La tarjeta debe tener 16 dígitos")
    private String numeroTarjeta;

    @NotBlank(message = "Titular obligatorio")
    private String titularTarjeta;

    @NotBlank(message = "Fecha expiración obligatoria")
    private String fechaExpiracion;

    @NotBlank(message = "CVV obligatorio")
    @Size(min = 3, max = 3, message = "CVV inválido")
    private String cvv;

    private String codigoCupon;
}