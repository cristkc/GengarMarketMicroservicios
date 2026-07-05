package com.gengarmarket.Carrito.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import java.util.ArrayList;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoItem> items = new ArrayList<>();
}
