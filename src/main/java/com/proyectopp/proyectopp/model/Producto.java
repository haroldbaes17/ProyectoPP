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
    private int stockTotal;

    @Column(name = "tipo_equipacion", length = 15, nullable = false)
    private String tipoEquipacion;

    @Column(name = "liga", length = 60, nullable = false)
    private String liga;

    @Column(name = "pais", length = 45, nullable = false)
    private String pais;

    @Column(name = "temporada", length = 15, nullable = false)
    private String temporada;

    @Column(name = "edicion_especial", nullable = false)
    private boolean edicionEspecial;

    @Column(name = "es_retro", nullable = false)
    private boolean esRetro;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "tipo_equipo", length = 40, nullable = false)
    private String tipoEquipo;


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

    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }

    public String getTipoEquipacion() {
        return tipoEquipacion;
    }

    public void setTipoEquipacion(String tipoEquipacion) {
        this.tipoEquipacion = tipoEquipacion;
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

    public boolean isEdicionEspecial() {
        return edicionEspecial;
    }

    public void setEdicionEspecial(boolean edicionEspecial) {
        this.edicionEspecial = edicionEspecial;
    }

    public boolean isEsRetro() {
        return esRetro;
    }

    public void setEsRetro(boolean esRetro) {
        this.esRetro = esRetro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", equipo='" + equipo + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", stockTotal=" + stockTotal +
                ", tipoEquipacion='" + tipoEquipacion + '\'' +
                ", liga='" + liga + '\'' +
                ", pais='" + pais + '\'' +
                ", temporada='" + temporada + '\'' +
                ", edicionEspecial=" + edicionEspecial +
                ", esRetro=" + esRetro +
                ", descripcion='" + descripcion + '\'' +
                ", tipoEquipo='" + tipoEquipo + '\'' +
                '}';
    }
}
