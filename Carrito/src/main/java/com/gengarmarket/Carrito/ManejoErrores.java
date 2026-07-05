package com.gengarmarket.Carrito;

import com.gengarmarket.Carrito.dto.ErrorDTO;
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
    public ResponseEntity<ErrorDTO> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validación",
                Map.of("mensaje", ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(
            NoSuchElementException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "No encontrado",
                Map.of("mensaje", ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Solicitud incorrecta",
                Map.of("dato", "Tipo de dato inválido"),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Error de validación de campos",
                errores,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(
                LocalDateTime.now(),
                500,
                "Error interno del servidor",
                Map.of("mensaje", "Ocurrió un error interno"),
                request.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}