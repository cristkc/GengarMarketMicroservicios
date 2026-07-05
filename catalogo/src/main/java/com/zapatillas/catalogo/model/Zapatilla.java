package com.zapatillas.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zapatilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zapatilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sku;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "La talla no puede estar vacía")
    private String talla;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer stock;
}