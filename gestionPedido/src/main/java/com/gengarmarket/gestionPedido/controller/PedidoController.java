package com.gengarmarket.gestionPedido.controller;

import com.gengarmarket.gestionPedido.model.Pedido;
import com.gengarmarket.gestionPedido.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @Operation(
        summary = "crear pedido",
        description = "crea un pedido a partir del usuario y del pago asociado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "pedido creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crear/{usuarioId}")
    public ResponseEntity<Pedido> crearPedido(@PathVariable Long usuarioId){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPedido(usuarioId));
    }

    @Operation(
        summary = "listar pedidos",
        description = "obtiene una lista de todos los pedidos registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){

        return ResponseEntity.ok(service.listar());
    }

    @Operation(
        summary = "buscar pedido",
        description = "busca un pedido por su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedido encontrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id){

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "actualizar pedido",
        description = "actualiza la informacion de un pedido existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedido actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Pedido pedido) {

        return ResponseEntity.ok(service.actualizar(id, pedido));
    }

    @Operation(
        summary = "eliminar pedido",
        description = "elimina un pedido existente del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedido eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> eliminar(@PathVariable Long id){

        service.eliminar(id);

        Map<String,String> respuesta = new HashMap<>();

        respuesta.put(
                "mensaje",
                "Pedido eliminado correctamente"
        );
        return ResponseEntity.ok(respuesta);
    }
}