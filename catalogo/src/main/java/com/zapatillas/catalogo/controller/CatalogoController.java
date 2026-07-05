package com.zapatillas.catalogo.controller;

import com.zapatillas.catalogo.model.Zapatilla;
import com.zapatillas.catalogo.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@RequiredArgsConstructor
@Tag(name = "Catálogo", description = "Endpoints para la gestión de zapatillas")
public class CatalogoController {

    private final CatalogoService service;

    @Operation(
            summary = "listar zapatillas",
            description = "obtiene la lista completa de zapatillas registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Zapatilla> listar() {
        return service.listar();
    }

    @Operation(
            summary = "buscar zapatilla por sku",
            description = "obtiene una zapatilla según su sku"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "zapatilla encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "zapatilla no encontrada"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @GetMapping("/buscarSku/{sku}")
    public ResponseEntity<Zapatilla> buscarPorSKU(@PathVariable Integer sku) {
        return ResponseEntity.ok(service.buscarPorSKU(sku));
    }

    @Operation(
            summary = "buscar zapatilla por nombre",
            description = "obtiene una zapatilla según su nombre"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "zapatilla encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "zapatilla no encontrada"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Zapatilla> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @Operation(
            summary = "crear zapatilla",
            description = "registra una nueva zapatilla en el catálogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "zapatilla creada correctamente"),
            @ApiResponse(responseCode = "400", description = "datos inválidos o zapatilla ya existente"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @PostMapping("/crear")
    public ResponseEntity<Zapatilla> crear(@Valid @RequestBody Zapatilla zapatilla) {
        Zapatilla guardada = service.guardar(zapatilla);
        return ResponseEntity.status(201).body(guardada);
    }

    @Operation(
            summary = "actualizar zapatilla",
            description = "actualiza una zapatilla existente del catálogo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "zapatilla actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "datos inválidos o sku faltante"),
            @ApiResponse(responseCode = "404", description = "zapatilla no encontrada"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @PutMapping("/actualizar")
    public ResponseEntity<Zapatilla> actualizar(@Valid @RequestBody Zapatilla zapatilla) {
        Zapatilla actualizada = service.actualizar(zapatilla);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(
            summary = "eliminar zapatilla",
            description = "elimina una zapatilla del catálogo según su sku"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "zapatilla eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "zapatilla no encontrada"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @DeleteMapping("/eliminar/{sku}")
    public ResponseEntity<String> eliminar(@PathVariable Integer sku) {
        service.eliminar(sku);
        return ResponseEntity.ok("Zapatilla eliminada correctamente");
    }
}