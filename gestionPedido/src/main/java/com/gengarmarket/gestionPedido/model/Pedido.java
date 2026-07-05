package com.gengarmarket.gestionPedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="pedidos")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Usuario obligatorio")
    private Long usuarioId;

    @NotBlank(message="Nombre obligatorio")
    @Size(min=3,max=60,message="Nombre entre 3 y 60 caracteres")
    private String nombreCliente;

    @NotBlank(message="Dirección obligatoria")
    private String direccion;

    @NotNull(message="Total obligatorio")
    @Positive(message="Total debe ser mayor a 0")
    private Double totalFinal;

    private LocalDate fechaCompra;

    private LocalDate fechaEntrega;

    @NotBlank(message="Estado obligatorio")
    private String estado;
}