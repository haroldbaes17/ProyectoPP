package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 45, nullable = false)
    private String apellidos;

    @Column(name = "cedula", length = 15, nullable = false, unique = true)
    private String cedula;

    @Column(name = "correo_electronico", length = 60, nullable = false, unique = true)
    private String correo_electronico;

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
}
