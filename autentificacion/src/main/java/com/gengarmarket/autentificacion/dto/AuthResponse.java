package com.gengarmarket.autentificacion.dto;

public class AuthResponse {

    private String mensaje;
    private Long usuarioId;
    private String nombreUsuario;

    public AuthResponse() {
    }

    public AuthResponse(String mensaje, Long usuarioId, String nombreUsuario) {
        this.mensaje = mensaje;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}