package com.descuento.cupones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descuento.cupones.model.Cupones;
import com.descuento.cupones.service.CuponesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cupones")
public class CuponesController {

    @Autowired
    private CuponesService service;

    @Operation(
        summary = "listar cupones",
        description = "obtiene una lista de todos los cupones registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "lista obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Cupones> listar(){
        return service.listar();
    }

    @Operation(
        summary = "buscar cupon por nombre",
        description = "obtiene un cupon segun su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "cupon encontrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Cupones> buscarPorNombre(@PathVariable String nombre){

        Cupones cupon = service.buscarPorNombre(nombre);

        return ResponseEntity.ok(cupon);
    }

    @Operation(
        summary = "crear cupon",
        description = "crea un nuevo cupon en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "cupon creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/create")
    public ResponseEntity<String> crear(@Valid @RequestBody Cupones cupones){

        service.guardar(cupones);

        return ResponseEntity.status(201).body("El cupon fue agregado con exito");
    }

    @Operation(
        summary = "eliminar cupon",
        description = "elimina un cupon existente por su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "cupon eliminado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){

        service.eliminar(id);

        return ResponseEntity.ok("Cupon eliminado correctamente");
    }

    @Operation(
        summary = "actualizar cupon",
        description = "actualiza la informacion de un cupon existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "cupon actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@Valid @RequestBody Cupones cupones){

        service.actualizar(cupones);

        return ResponseEntity.ok("Cupon actualizado correctamente");
    }

    @Operation(
        summary = "buscar cupon por codigo",
        description = "obtiene un cupon segun su codigo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "cupon encontrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Cupones> buscarPorCodigo(@PathVariable String codigo){

        Cupones cupon = service.buscarPorCodigo(codigo);

        return ResponseEntity.ok(cupon);
    }
}