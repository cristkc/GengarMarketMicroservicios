package com.reportes.reportess.controller;
import com.reportes.reportess.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reportes.reportess.model.*;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController

@RequestMapping("/reportes")

public class ReportesController {
    @Autowired  
    private ReportesServices service;

    @Operation(
    summary ="Listar los reportes por id",
    description="obtiene reportes especifipor por la busqueda por id"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "lista obtenidad correctamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
@GetMapping("/{id}")
    public Optional<reportes> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
    @Operation(
    summary ="agregar reportes",
    description="ingresa la fecha y la ganancia para poder generar un reporte"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "201",description = "Creado existosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})


    @PostMapping("/agregar")
    public ResponseEntity<reportes> guardarReportes(@Valid @RequestBody reportes repo) {
        
        reportes nueva = service.guardarReportes(repo);
        
        return ResponseEntity.status(201).body(nueva);
    }
    @Operation(
    summary ="eliminar reportes",
    description="Seleccione la id del reporte que quieras eliminar"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Eliminado  exitosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description= "Error interno del servidor ")
})
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        Optional<reportes> repo = service.buscarPorId(id);

        if(repo.isPresent()){
            service.eliminarPorId(id);
            return "se elimino existosamente creo";

        }else{
            return"reporte no encontrado por id "+ id;
        }
    }

    @Operation(
    summary ="Actualiza reportes",
    description="agrega una id ingresada y ingresa la fecha y la ganancia para modificar un reporte"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "reporte actualizado exitosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @PutMapping("/actualizar/{id}")
public String actualizar(@PathVariable Integer id, @RequestBody reportes re) {
    Optional<reportes> repo = service.buscarPorId(id);
    if(repo.isPresent()){
        service.actualizarPorId(id, re);
        return "reporte actualizado";
    }else{

    
    return"Comentario no encontrado por id"+id;
}}

@Operation(
    summary ="Listar los pagos",
    description="obtiene los pagos del microservicio de gestion de pago"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "lista de pago obtenidad correctamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @GetMapping("/pagos")
public ResponseEntity<List<Integer>> verPagos(){

    return ResponseEntity.ok(
            service.mostrarPagosTotales()
    );
}
@Operation(
    summary ="Listar los Ganancia",
    description="obtiene todas las Ganancias de la tienda"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "lista de pago obtenidad correctamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})
    @GetMapping("/ganancias")
public ResponseEntity<reportes> verGanancias(){

    return ResponseEntity.ok(
            service.listaReportes()
    );
}
    
    

}
