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

    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "equipo", length = 60)
    private String equipo;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "imagen", length = 200, nullable = false)
    private String imagen;

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

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getStock_total() {
        return stock_total;
    }

    public void setStock_total(int stock_total) {
        this.stock_total = stock_total;
    }

    public String getTipo_equipacion() {
        return tipo_equipacion;
    }

    public void setTipo_equipacion(String tipo_equipacion) {
        this.tipo_equipacion = tipo_equipacion;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public boolean isEdicion_especial() {
        return edicion_especial;
    }

    public void setEdicion_especial(boolean edicion_especial) {
        this.edicion_especial = edicion_especial;
    }

    public boolean isEs_retro() {
        return es_retro;
    }

    public void setEs_retro(boolean es_retro) {
        this.es_retro = es_retro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
