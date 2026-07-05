package com.gengarmarket.certificacion.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CertificacionRequest {

    @NotBlank(message="El nombre de quien recibe es obligatorio")
    private String recibidoPor;

    @NotBlank(message="El rut es obligatorio")
    private String rutRecibe;

    @NotBlank(message="El parentesco es obligatorio")
    private String parentesco;
}