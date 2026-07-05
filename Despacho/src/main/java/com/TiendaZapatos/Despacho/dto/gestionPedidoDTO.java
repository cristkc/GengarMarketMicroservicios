package com.TiendaZapatos.Despacho.dto;


import lombok.Data;

@Data
public class gestionPedidoDTO {
     private Long id;
    private String direccionDelusuario;
    private String nombreDelDespachador;
    private String apellidoDelDespachador;
    private String patenteCamion;
    private String fechaSalida;

}
