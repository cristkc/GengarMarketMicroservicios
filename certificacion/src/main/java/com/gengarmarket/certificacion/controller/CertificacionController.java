package com.gengarmarket.certificacion.controller;

import com.gengarmarket.certificacion.dto.CertificacionRequest;
import com.gengarmarket.certificacion.model.Certificacion;
import com.gengarmarket.certificacion.service.CertificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/certificacion")
@RequiredArgsConstructor
public class CertificacionController {

    private final CertificacionService service;

    @Operation(
        summary = "crear certificacion",
        description = "crea una certificacion de entrega a partir del id de un pedido entregado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "certificacion creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o pedido aun no entregado"),
        @ApiResponse(responseCode = "404", description = "Pedido o certificacion no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crear/{pedidoId}")
    public ResponseEntity<Certificacion> crear(
            @PathVariable Long pedidoId,
            @Valid @RequestBody CertificacionRequest request){

        return ResponseEntity.status(201).body(service.crear(pedidoId, request));
    }

    @Operation(
        summary = "listar certificaciones",
        description = "obtiene una lista de todas las certificaciones registradas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Certificacion> listar(){
        return service.listar();
    }

    @Operation(
        summary = "buscar certificacion por id",
        description = "obtiene una certificacion segun su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "certificacion encontrada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public Certificacion buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @Operation(
        summary = "actualizar certificacion",
        description = "actualiza los datos de recepcion de una certificacion existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "certificacion actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Certificacion> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CertificacionRequest request){

        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @Operation(
        summary = "eliminar certificacion",
        description = "elimina una certificacion existente por su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "certificacion eliminada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "404", description = "Certificacion no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String,String>> eliminar(@PathVariable Long id){

        service.eliminar(id);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje",
                        "Certificación eliminado correctamente"
                )
        );
    }
}