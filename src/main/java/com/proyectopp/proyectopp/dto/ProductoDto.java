package com.proyectopp.proyectopp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductoDto {

    @NotEmpty(message = "Este campo es requerido*")
    private String nombre;

    @NotEmpty(message = "Este campo es requerido*")
    private String equipo;

    @NotNull(message = "Este campo es requerido*")
    @Min(1)
    private BigDecimal precio;

    private MultipartFile imagen;

    @Min(1)
    private int stock_total;

    @NotEmpty(message = "Este campo es requerido*")
    private String tipo_equipacion;

    @NotEmpty(message = "Este campo es requerido*")
    private String liga;

    @NotEmpty(message = "Este campo es requerido*")
    private String pais;

    @NotEmpty(message = "Este campo es requerido*")
    private String temporada;

    @NotNull(message = "Este campo es requerido*")
    private Boolean edicion_especial;

    @NotNull(message = "Este campo es requerido*")
    private Boolean es_retro;

    @Size(min = 10, max = 2000, message = "La descripción debe tener entre 10 y 2000 caracteres")
    private String descripcion;

    // Getters y Setters
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

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
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

    public @NotNull(message = "Este campo es requerido") Boolean getEdicion_especial() {
        return edicion_especial;
    }

    public void setEdicion_especial(@NotNull(message = "Este campo es requerido") Boolean edicion_especial) {
        this.edicion_especial = edicion_especial;
    }

    public @NotNull(message = "Este campo es requerido") Boolean getEs_retro() {
        return es_retro;
    }

    public void setEs_retro(@NotNull(message = "Este campo es requerido") Boolean es_retro) {
        this.es_retro = es_retro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
