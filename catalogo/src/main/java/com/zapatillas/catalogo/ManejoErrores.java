package com.zapatillas.catalogo;

import com.zapatillas.catalogo.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ManejoErrores {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarDuplicado(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("zapatilla", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Solicitud incorrecta",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(
            NoSuchElementException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("zapatilla", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "No encontrado",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validación",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> manejarTipoDato(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("dato", "Tipo de dato inválido");

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Solicitud incorrecta",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarGeneral(
            Exception ex,
            HttpServletRequest request) {

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