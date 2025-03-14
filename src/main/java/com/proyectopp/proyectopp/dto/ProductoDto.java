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
    private int stockTotal;

    @NotEmpty(message = "Este campo es requerido*")
    private String tipoEquipacion;

    @NotEmpty(message = "Este campo es requerido*")
    private String liga;

    @NotEmpty(message = "Este campo es requerido*")
    private String pais;

    @NotEmpty(message = "Este campo es requerido*")
    private String temporada;

    @NotNull(message = "Este campo es requerido*")
    private Boolean edicionEspecial;

    @NotNull(message = "Este campo es requerido*")
    private Boolean esRetro;

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

    public @NotNull(message = "Este campo es requerido") Boolean getEdicionEspecial() {
        return edicionEspecial;
    }

    public void setEdicionEspecial(@NotNull(message = "Este campo es requerido") Boolean edicionEspecial) {
        this.edicionEspecial = edicionEspecial;
    }

    public @NotNull(message = "Este campo es requerido") Boolean getEsRetro() {
        return esRetro;
    }

    public void setEsRetro(@NotNull(message = "Este campo es requerido") Boolean esRetro) {
        this.esRetro = esRetro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ProductoDto{" +
                "nombre='" + nombre + '\'' +
                ", equipo='" + equipo + '\'' +
                ", precio=" + precio +
                ", imagen=" + imagen +
                ", stockTotal=" + stockTotal +
                ", tipoEquipacion='" + tipoEquipacion + '\'' +
                ", liga='" + liga + '\'' +
                ", pais='" + pais + '\'' +
                ", temporada='" + temporada + '\'' +
                ", edicionEspecial=" + edicionEspecial +
                ", esRetro=" + esRetro +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
