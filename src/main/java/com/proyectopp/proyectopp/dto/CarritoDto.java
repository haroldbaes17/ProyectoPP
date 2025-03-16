package com.proyectopp.proyectopp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CarritoDto {

    @NotNull( message = "El ID del producto es requerido")
    private int idProducto;

    @NotEmpty(message = "Debes elegir una talla*")
    private String talla;


    @Min(value = 1, message = "Debes elegir al menos 1 articulo*")
    private int cantidad;

    @NotNull(message = "Campo requerido")
    private BigDecimal precioUnitario;

    @NotNull(message = "El ID del producto es requerido")
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(@NotNull(message = "El ID del producto es requerido") int idProducto) {
        this.idProducto = idProducto;
    }

    public @NotEmpty(message = "Debes elegir una talla*") String getTalla() {
        return talla;
    }

    public void setTalla(@NotEmpty(message = "Debes elegir una talla*") String talla) {
        this.talla = talla;
    }

    @Min(value = 1, message = "Debes elegir al menos 1 articulo*")
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(@Min(value = 1, message = "Debes elegir al menos 1 articulo*") int cantidad) {
        this.cantidad = cantidad;
    }

    public @NotNull(message = "Campo requerido") BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(@NotNull(message = "Campo requerido") BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return "CarritoDto{" +
                "idProducto=" + idProducto +
                ", talla='" + talla + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
