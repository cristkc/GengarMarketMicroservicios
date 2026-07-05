package com.gengarmarket.certificacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="certificaciones")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificacion {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @NotNull(message="Usuario obligatorio")
    private Long usuarioId;

    @NotBlank(message="Nombre obligatorio")
    private String nombreCliente;

    @NotBlank(message="Dirección obligatoria")
    private String direccion;

    @Positive(message="Total inválido")
    private Double totalFinal;

    @NotBlank(message="Persona que recibe obligatoria")
    private String recibidoPor;

    @NotBlank(message="Rut obligatorio")
    private String rutRecibe;

    @NotBlank(message="Parentesco obligatorio")
    private String parentesco;

    private LocalDate fechaRecepcion;

    private String estadoEntrega;
}