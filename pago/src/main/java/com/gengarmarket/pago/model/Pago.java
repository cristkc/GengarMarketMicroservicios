package com.gengarmarket.pago.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message="El nombre es obligatorio")
    @Size(min=3,max=100,message="El nombre debe tener entre 3 y 100 caracteres")
    private String nombreCliente;

    @NotBlank(message="El correo es obligatorio")
    @Email(message="Correo inválido")
    private String correo;

    @NotBlank(message="La dirección es obligatoria")
    private String direccion;


    @NotBlank(message="Número de tarjeta obligatorio")
    @Pattern(regexp="^[0-9]{16}$",message="La tarjeta debe tener 16 números")
    private String numeroTarjeta;

    @NotBlank(message="Titular obligatorio")
    private String titularTarjeta;

    @NotBlank(message="Fecha expiración obligatoria")
    private String fechaExpiracion;

    @NotBlank(message="CVV obligatorio")
    @Pattern(regexp="^[0-9]{3}$",message="CVV inválido")
    private String cvv;

    private String codigoCupon;

    @NotNull(message="Subtotal obligatorio")
    @Positive(message="Subtotal inválido")
    private Integer subtotal;

    private Integer descuento;

    @NotNull(message="Total obligatorio")
    @Positive(message="Total inválido")
    private Integer totalFinal;

    @NotBlank(message="Estado obligatorio")
    private String estadoPedido;

    private String mensaje;

    private LocalDateTime fechaPago;

}