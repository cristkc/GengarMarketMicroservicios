package com.gengarmarket.autentificacion;

import com.gengarmarket.autentificacion.dto.LoginRequest;
import com.gengarmarket.autentificacion.dto.RegisterRequest;
import com.gengarmarket.autentificacion.dto.UpdateUserRequest;
import com.gengarmarket.autentificacion.model.Usuario;
import com.gengarmarket.autentificacion.repository.UsuarioRepository;
import com.gengarmarket.autentificacion.service.AutentificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutentificacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AutentificacionService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreUsuario("jose");
        usuario.setContrasena("123456");
    }

    @Test
    void registerDebeCrearUsuarioCuandoNoExiste() {
        RegisterRequest request = new RegisterRequest();
        request.setNombreUsuario("jose");
        request.setContrasena("123456");

        when(usuarioRepository.existsByNombreUsuario("jose")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("claveEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        var response = service.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void registerDebeRetornarBadRequestCuandoUsuarioExiste() {
        RegisterRequest request = new RegisterRequest();
        request.setNombreUsuario("jose");
        request.setContrasena("123456");

        when(usuarioRepository.existsByNombreUsuario("jose")).thenReturn(true);

        var response = service.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void loginDebeRetornarOkCuandoCredencialesSonCorrectas() {
        LoginRequest request = new LoginRequest();
        request.setNombreUsuario("jose");
        request.setContrasena("123456");

        when(usuarioRepository.findByNombreUsuario("jose")).thenReturn(Optional.of(usuario));

        var response = service.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void loginDebeRetornarUnauthorizedCuandoCredencialesSonIncorrectas() {
        LoginRequest request = new LoginRequest();
        request.setNombreUsuario("jose");
        request.setContrasena("mala");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Credenciales incorrectas"));

        var response = service.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void existeUsuarioDebeRetornarTrueCuandoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean resultado = service.existeUsuario(1L);

        assertTrue(resultado);
        verify(usuarioRepository).existsById(1L);
    }

    @Test
    void existeUsuarioDebeRetornarFalseCuandoIdEsInvalido() {
        boolean resultado = service.existeUsuario(0L);

        assertFalse(resultado);
        verify(usuarioRepository, never()).existsById(any());
    }

    @Test
    void obtenerUsuarioDebeRetornarOkCuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var response = service.obtenerUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void actualizarUsuarioDebeActualizarCuandoExiste() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setNombreUsuario("joseNuevo");
        request.setContrasena("654321");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByNombreUsuario("joseNuevo")).thenReturn(false);
        when(passwordEncoder.encode("654321")).thenReturn("claveNueva");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        var response = service.actualizarUsuario(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void eliminarUsuarioDebeEliminarCuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var response = service.eliminarUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioRepository).delete(usuario);
    }
}