package com.gengarmarket.autentificacion.service;

import com.gengarmarket.autentificacion.dto.AuthResponse;
import com.gengarmarket.autentificacion.dto.LoginRequest;
import com.gengarmarket.autentificacion.dto.RegisterRequest;
import com.gengarmarket.autentificacion.dto.UpdateUserRequest;
import com.gengarmarket.autentificacion.dto.UserResponse;
import com.gengarmarket.autentificacion.model.Usuario;
import com.gengarmarket.autentificacion.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AutentificacionService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AutentificacionService(UsuarioRepository usuarioRepository,
                                  PasswordEncoder passwordEncoder,
                                  AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        AuthResponse response = new AuthResponse(
                "Usuario registrado correctamente",
                usuarioGuardado.getId(),
                usuarioGuardado.getNombreUsuario()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNombreUsuario(),
                            request.getContrasena()
                    )
            );

            Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario())
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

            AuthResponse response = new AuthResponse(
                    "Login correcto",
                    usuario.getId(),
                    usuario.getNombreUsuario()
            );

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
    }

    public boolean existeUsuario(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("El id del usuario es inválido");
        }

        return usuarioRepository.existsById(usuarioId);
    }

    public ResponseEntity<UserResponse> obtenerUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        UserResponse response = new UserResponse(
                usuario.getId(),
                usuario.getNombreUsuario()
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AuthResponse> actualizarUsuario(Long usuarioId, UpdateUserRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if (!usuario.getNombreUsuario().equals(request.getNombreUsuario())
                && usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        AuthResponse response = new AuthResponse(
                "Usuario actualizado correctamente",
                usuarioActualizado.getId(),
                usuarioActualizado.getNombreUsuario()
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> eliminarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuario eliminado correctamente")
        );
    }
}