package com.gengarmarket.Carrito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "carrito_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)   
    @JoinColumn(name = "carrito_id", nullable = false)
    @JsonIgnore                          
    private Carrito carrito;

    private Integer sku;
    private String nombre;
    private String marca;
    private Integer precioUnitario;
    private Integer cantidad;
}