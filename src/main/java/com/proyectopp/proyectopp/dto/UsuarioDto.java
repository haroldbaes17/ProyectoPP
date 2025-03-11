package com.proyectopp.proyectopp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDto {
    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellidos;

    @NotEmpty
    private String cedula;

    @NotEmpty
    private String correoElectronico;

    @Size(min = 8, message = "El contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotEmpty
    private String telefono;

    private Boolean es_admin;

    private Boolean confirmado;

    private String token;

    public @NotEmpty String getNombre() {
        return nombre;
    }

    public void setNombre(@NotEmpty String nombre) {
        this.nombre = nombre;
    }

    public @NotEmpty String getApellidos() {
        return apellidos;
    }

    public void setApellidos(@NotEmpty String apellidos) {
        this.apellidos = apellidos;
    }

    public @NotEmpty String getCedula() {
        return cedula;
    }

    public void setCedula(@NotEmpty String cedula) {
        this.cedula = cedula;
    }

    public @NotEmpty String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(@NotEmpty String correo_electronico) {
        this.correoElectronico = correo_electronico;
    }

    public @Size(min = 8, message = "El contraseña debe tener al menos 8 caracteres") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "El contraseña debe tener al menos 8 caracteres") String password) {
        this.password = password;
    }

    public @NotEmpty String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NotEmpty String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEs_admin() {
        return es_admin;
    }

    public void setEs_admin(Boolean es_admin) {
        this.es_admin = es_admin;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
