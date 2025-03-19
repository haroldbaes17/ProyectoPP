package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 45, nullable = false)
    private String apellidos;

    @Column(name = "cedula", length = 15, nullable = false, unique = true)
    private String cedula;

    @Column(name = "correo_electronico", length = 60, nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "telefono", length = 20, nullable = false)
    private String telefono;

    @Column(name = "es_admin", nullable = false)
    private boolean es_admin;

    @Column(name = "confirmado", nullable = false)
    private boolean confirmado;

    @Column(name = "token", length = 64, nullable = false)
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correo_electronico) {
        this.correoElectronico = correo_electronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEs_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", es_admin=" + es_admin +
                ", confirmado=" + confirmado +
                ", token='" + token + '\'' +
                '}';
    }
}
