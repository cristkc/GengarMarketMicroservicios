package com.descuento.cupones;

import com.descuento.cupones.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejoErrores {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarDuplicado(
            IllegalArgumentException ex,
            HttpServletRequest request){

        Map<String, String> errores = new HashMap<>();
        errores.put("cupon", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Solicitud incorrecta",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(
            RuntimeException ex,
            HttpServletRequest request){

        Map<String, String> errores = new HashMap<>();
        errores.put("cupon", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "No encontrado",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(
            Exception ex,
            HttpServletRequest request){

        Map<String, String> errores = new HashMap<>();
        errores.put("error", "Ocurrió un error interno del servidor");

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                500,
                "Error interno del servidor",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(errorDTO);
    }
}