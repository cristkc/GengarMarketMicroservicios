package com.TiendaZapatos.Despacho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="despachos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message ="no puede el nombre estar vacio")
    @NotNull(message = "no puede dejar el nombre como nulo")
    private String nombre;
    @NotBlank(message ="no puede el apellido estar vacio")
    @NotNull(message = "no puede dejar el apellido como nulo")
    private String apellido;
    @NotBlank(message ="no puedes dejar la patente  vacio")
    @NotNull(message = "la patente no puede estar nulo")
    private String patenteCamion;
    @NotBlank(message ="no puedes dejar la fecha vacio")
    @NotNull(message = "la fecha no puede estar nulo")
    private String fechaSalida;

    


}
