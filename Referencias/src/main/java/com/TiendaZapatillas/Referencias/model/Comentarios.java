package com.TiendaZapatillas.Referencias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name="comentarios")
@Data
@NoArgsConstructor
@AllArgsConstructor




public class Comentarios {
     @Id
     @GeneratedValue(strategy =  GenerationType.IDENTITY)
     private Integer id;


     @NotBlank(message="se necesita el nombre del producto")
     private String nombreDeProducto;

     @NotBlank(message="la descripcion o comentario no debe estar vacio")
     @Column(nullable = false)
     private String comentario;
     
  
     




}
