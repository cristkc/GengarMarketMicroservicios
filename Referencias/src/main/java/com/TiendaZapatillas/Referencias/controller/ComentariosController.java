package com.TiendaZapatillas.Referencias.controller;
import com.TiendaZapatillas.Referencias.model.Comentarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.TiendaZapatillas.Referencias.service.ComentariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;







@RestController
@RequestMapping("/comentarios")
public class ComentariosController {
@Autowired
private ComentariosService service;
@Operation(
    summary ="Ver todos los comentarios",
    description="permite ver todo los comentarios agregados"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "listado existosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})



@GetMapping("/verTodoComentarios")
public List<Comentarios> listar() {
    return service.listar();
}
@Operation(
    summary ="agregar Comentario",
    description="permite crear un Comentario y pide la id del usuario para verificar que existe"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "201",description = "Creado existosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})

 @PostMapping("/agregar/{usuarioId}")
    public ResponseEntity<?> guardar( @PathVariable Integer usuarioId,@Valid @RequestBody Comentarios comentario) {

        return ResponseEntity.status(201).body(service.guardar(usuarioId, comentario));
    }

@Operation(
    summary ="Elimina Comentarios",
    description="Seleccione la id del Comentario que quieras eliminar"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Eliminado  exitosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description= "Error interno del servidor ")
})



@DeleteMapping("/Eliminar/{id}")
public String eliminar(@PathVariable Integer id){
    Optional<Comentarios> come = service.buscarPorId(id);

    if(come.isPresent()){
        service.eliminarPorId(id);
        return "se a eliminado el comentario con id: "+id;
    }else{

    return "comentario no encontrado por id";
    }
}
@Operation(
    summary ="Actualiza Comentario",
    description="Permite actualizar un comentario con la id"
)
@ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Comentario actualizado exitosamente"),
    @ApiResponse(responseCode = "400",description = "datos invalidos"),
    @ApiResponse(responseCode = "500", description="Error interno del servidor ")
})


@PutMapping("/actualizar/{id}")
public String actualizar(@PathVariable Integer id, @RequestBody Comentarios com) {
  
    Optional<Comentarios> existente = service.buscarPorId(id);
    if(existente.isPresent()){
        service.actualizar(id, com);
        return "comentario actualisado";

    }else{
    
    return "comentario no encontrado con id: "+id;
}



}}