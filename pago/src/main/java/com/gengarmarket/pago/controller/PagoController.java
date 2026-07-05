package com.gengarmarket.pago.controller;

import com.gengarmarket.pago.dto.PagoRequest;
import com.gengarmarket.pago.dto.PagoResponse;
import com.gengarmarket.pago.model.Pago;
import com.gengarmarket.pago.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @Operation(
        summary = "lista de pagos",
        description = "obtiene una lista de pagos realizados exitosamente del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Pago>> listarPagos(){

        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @Operation(
        summary = "buscar pago por usuario",
        description = "obtiene un pago segun el id del usuario"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pago encontrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Pago> buscarPorUsuario(
            @PathVariable Long usuarioId){

        return ResponseEntity.ok(
                pagoService.buscarPorUsuario(usuarioId)
        );
    }

    @Operation(
        summary = "eliminar pago",
        description = "elimina un pago existente del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pago eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> eliminarPago(@PathVariable Long id){

        pagoService.eliminarPago(id);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Pago eliminado correctamente"
                )
        );
    }

    @Operation(
        summary = "actualizar pago",
        description = "actualiza la informacion de un pago existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pago actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoRequest request){

        return ResponseEntity.ok(pagoService.actualizarPago(id,request));
    }

    @Operation(
        summary = "realizar pago",
        description = "procesa un nuevo pago en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "pago realizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<PagoResponse> realizarPago(@Valid @RequestBody PagoRequest request){

        PagoResponse response = pagoService.procesarPago(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}