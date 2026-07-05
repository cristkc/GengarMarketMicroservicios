package com.gengarmarket.Carrito.controller;

import com.gengarmarket.Carrito.dto.ActualizarCantidadRequest;
import com.gengarmarket.Carrito.dto.CarritoResponse;
import com.gengarmarket.Carrito.model.CarritoItemRequest;
import com.gengarmarket.Carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Endpoints para gestionar el carrito de compras")
public class CarritoController {

    private final CarritoService service;

    @Operation(
            summary = "Agregar producto al carrito",
            description = "Agrega un producto al carrito de un usuario validando usuario y stock"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregar/{usuarioId}")
    public ResponseEntity<CarritoResponse> agregarItem(
            @PathVariable Long usuarioId,
            @Valid @RequestBody CarritoItemRequest request) {

        CarritoResponse response = service.agregarItem(
                usuarioId,
                request.getSku(),
                request.getCantidad()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Obtener carrito por usuario",
            description = "Obtiene el carrito asociado a un usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Usuario inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponse> obtenerCarrito(@PathVariable Long usuarioId) {
        CarritoResponse response = service.obtenerCarrito(usuarioId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar producto del carrito",
            description = "Elimina un producto del carrito según el usuario y el sku"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado en el carrito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{usuarioId}/{sku}")
    public ResponseEntity<CarritoResponse> eliminarItem(
            @PathVariable Long usuarioId,
            @PathVariable Integer sku) {

        CarritoResponse response = service.eliminarItem(usuarioId, sku);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Vaciar carrito",
            description = "Elimina todos los productos del carrito de un usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito vaciado correctamente"),
            @ApiResponse(responseCode = "404", description = "Carrito vacío o no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity<CarritoResponse> vaciarCarrito(@PathVariable Long usuarioId) {
        CarritoResponse response = service.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualizar cantidad de producto",
            description = "Actualiza la cantidad de un producto en el carrito validando stock"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado en el carrito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar/{usuarioId}/{sku}")
    public ResponseEntity<CarritoResponse> actualizarCantidad(
            @PathVariable Long usuarioId,
            @PathVariable Integer sku,
            @Valid @RequestBody ActualizarCantidadRequest request) {

        CarritoResponse response = service.actualizarCantidad(
                usuarioId,
                sku,
                request.getCantidad()
        );

        return ResponseEntity.ok(response);
    }
}