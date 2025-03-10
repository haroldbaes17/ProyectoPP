package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "equipo", length = 60)
    private String equipo;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock_total", nullable = false)
    private int stock_total;

    @Column(name = "tipo_equipacion", length = 15, nullable = false)
    private String tipo_equipacion;

    @Column(name = "liga", length = 60, nullable = false)
    private String liga;

    @Column(name = "pais", length = 45, nullable = false)
    private String pais;

    @Column(name = "temporada", length = 15, nullable = false)
    private String temporada;

    @Column(name = "edicion_especial", nullable = false)
    private boolean edicion_especial;

    @Column(name = "es_retro", nullable = false)
    private boolean es_retro;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;
}
