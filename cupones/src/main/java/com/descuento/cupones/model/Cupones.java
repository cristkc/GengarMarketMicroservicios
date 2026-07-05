package com.descuento.cupones.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name ="cupones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cupones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String codigo;

    @NotNull(message = " no puede estar vacio")
    @Min(1)
    @Max(100)
    private Integer porcentaje;
}
