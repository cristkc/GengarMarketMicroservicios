package com.TiendaZapatos.Despacho.controller;
import com.TiendaZapatos.Despacho.service.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.TiendaZapatos.Despacho.model.despacho;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@RestController
@RequestMapping("/despacho")

public class despachoController {
    @Autowired
    private despachoService service;
    @Operation(
    summary ="ver todos los  despacho",
    description="permite ver todo los despachos hechos"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Listado existosamente"),
    @ApiResponse(responseCode = "400",description = "Datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})

    @GetMapping("/listar")
    public List<despacho> listar() {
        return service.listar();
    }
@Operation(
    summary ="ver el despacho marcado por la id",
    description="permite ver el despacho seleccionado por la id"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Listado existosamente"),
    @ApiResponse(responseCode = "400",description = "Datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})


    

    @GetMapping("/despachos/{id}")
    public Optional<despacho> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
@Operation(
    summary ="Crear los despachos",
    description="permite crear un despacho"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "201",description = "Creado existosamente"),
    @ApiResponse(responseCode = "400",description = "Datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @PostMapping("/agregar")
    public ResponseEntity<despacho> guardarDespachador(@Valid @RequestBody despacho repo) {
        
        despacho nueva = service.guardarDespachador(repo);
        
        return ResponseEntity.status(201).body(nueva);
    }

    @Operation(
    summary ="eliminar un despacho",
    description="permite eliminar un despacho que es buscado por la id"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Eliminado existosamente"),
    @ApiResponse(responseCode = "400",description = "Datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        Optional<despacho> repo = service.buscarPorId(id);

        if(repo.isPresent()){
            service.eliminarPorId(id);
            return "se elimino existosamente creo";

        }else{
            return"reporte no encontrado por id "+ id;
        }
    }
    @Operation(
    summary ="Actualizar  despachos",
    description="permite actualizar un despacho que es selecionado por la id"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "listado existosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @PutMapping("/actualizar/{id}")
public String actualizar(@PathVariable Integer id, @RequestBody despacho des) {
    Optional<despacho> despa = service.buscarPorId(id);
    if(despa.isPresent()){
        service.actualizarPorId(id, des);
        return "despacho actualizado";
    }else{

    
    return"Comentario no encontrado por id"+id;
}}

@Operation(
    summary ="permite ver al despachador que va entregar el pedido al cliente",
    description="la primera id es al del despachador y la segunda es del cliente"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "enviado existosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})


     @GetMapping("/info/{despachoId}/{pedidoId}")
    public ResponseEntity<String> obtenerInfo( @PathVariable Integer despachoId,@PathVariable Long pedidoId){

        return ResponseEntity.ok(
                service.obtenerInformacionDespacho(despachoId,pedidoId));
    }


}
