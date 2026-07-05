package com.gengarmarket.autentificacion.controller;

import com.gengarmarket.autentificacion.dto.LoginRequest;
import com.gengarmarket.autentificacion.dto.RegisterRequest;
import com.gengarmarket.autentificacion.dto.UpdateUserRequest;
import com.gengarmarket.autentificacion.service.AutentificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentificación", description = "Endpoints para registro, login y gestión de usuarios")
public class AuthController {

    private final AutentificacionService autentificacionService;

    public AuthController(AutentificacionService autentificacionService) {
        this.autentificacionService = autentificacionService;
    }

    @Operation(
            summary = "registrar usuario",
            description = "registra un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "datos inválidos o usuario ya existente"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return autentificacionService.register(request);
    }

    @Operation(
            summary = "iniciar sesión",
            description = "autentica un usuario con nombre de usuario y contraseña"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login realizado correctamente"),
            @ApiResponse(responseCode = "400", description = "datos inválidos o credenciales incorrectas"),
            @ApiResponse(responseCode = "404", description = "usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return autentificacionService.login(request);
    }

    @Operation(
            summary = "validar usuario",
            description = "verifica si un usuario existe según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "validación realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "id inválido"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @GetMapping("/validar/{usuarioId}")
    public boolean validarUsuario(@PathVariable Long usuarioId) {
        return autentificacionService.existeUsuario(usuarioId);
    }

    @Operation(
            summary = "obtener usuario por id",
            description = "obtiene los datos básicos de un usuario según su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long usuarioId) {
        return autentificacionService.obtenerUsuario(usuarioId);
    }

    @Operation(
            summary = "actualizar usuario",
            description = "actualiza el nombre de usuario y la contraseña de un usuario existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "datos inválidos o nombre de usuario en uso"),
            @ApiResponse(responseCode = "404", description = "usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @PutMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long usuarioId,
                                               @Valid @RequestBody UpdateUserRequest request) {
        return autentificacionService.actualizarUsuario(usuarioId, request);
    }

    @Operation(
            summary = "eliminar usuario",
            description = "elimina un usuario existente del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "error interno del servidor")
    })
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long usuarioId) {
        return autentificacionService.eliminarUsuario(usuarioId);
    }
}