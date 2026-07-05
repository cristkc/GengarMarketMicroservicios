package com.TiendaZapatillas.Referencias;
import com.TiendaZapatillas.Referencias.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ManejadorErrores {
   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errores.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                400,
                "Solicitud incorrecta",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> manejarBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();
        errores.put("pago", ex.getMessage());

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
        errores.put("recurso", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                404,
                "Recurso no encontrado",
                errores,
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(errorDTO);
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
public ResponseEntity<ErrorDTO> manejarErrorGeneral(
        Exception ex,
        HttpServletRequest request) {

    ex.printStackTrace();

    Map<String, String> errores = new HashMap<>();
    errores.put("error", ex.getMessage());

    ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            500,
            "Error interno del servidor",
            errores,
            request.getRequestURI()
    );

    return ResponseEntity.status(500).body(errorDTO);
}
    @ExceptionHandler(NoHandlerFoundException.class)
public ResponseEntity<ErrorDTO> manejar404Url(
        NoHandlerFoundException ex,
        HttpServletRequest request) {

    Map<String, String> errores = new HashMap<>();
    errores.put("ruta", "La URL no existe");

    ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            404,
            "Ruta no encontrada",
            errores,
            request.getRequestURI()
    );

    return ResponseEntity.status(404).body(errorDTO);
}
}


    



