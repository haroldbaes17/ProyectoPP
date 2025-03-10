package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "provincia", length = 15, nullable = false)
    private String provincia;

    @Column(name = "canton", length = 30, nullable = false)
    private String canton;

    @Column(name = "distrito", length = 30, nullable = false)
    private String distrito;

    @Column(name = "direccion_exacta", columnDefinition = "TEXT", nullable = false)
    private String direccion_exacta;
}
