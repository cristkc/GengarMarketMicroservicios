package com.reportes.reportess.model;


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
@Table(name="reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class reportes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "la fecha no puede estar vacia")
    @NotNull(message = "la fecha no puede estar nulo")
    private String fecha;
      
    @Min(value = 0,message = "la ganancia no puede ser negativa") /// los errores de integer no funcionan
    @NotNull(message = "la ganancia no puede ser nula")
    private Integer ganancia;

}