package com.gengarmarket.gestionPedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;

    private int status;

    private String mensaje;

    private Map<String,String> errores;

    private String path;

}