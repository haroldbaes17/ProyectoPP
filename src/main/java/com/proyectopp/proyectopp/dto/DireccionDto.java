package com.proyectopp.proyectopp.dto;

import jakarta.validation.constraints.NotEmpty;

public class DireccionDto {

    @NotEmpty(message = "Debes elegir una provincia*")
    private String provincia;

    @NotEmpty(message = "Debes elegir un cantón*")
    private String canton;

    @NotEmpty(message = "Debes elegir un distrito*")
    private String distrito;

    @NotEmpty(message = "Debes ingresar tu direccion exacta")
    private String direccionExacta;

    public @NotEmpty(message = "Debes elegir una provincia*") String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NotEmpty(message = "Debes elegir una provincia*") String provincia) {
        this.provincia = provincia;
    }

    public @NotEmpty(message = "Debes elegir un cantón*") String getCanton() {
        return canton;
    }

    public void setCanton(@NotEmpty(message = "Debes elegir un cantón*") String canton) {
        this.canton = canton;
    }

    public @NotEmpty(message = "Debes elegir un distrito*") String getDistrito() {
        return distrito;
    }

    public void setDistrito(@NotEmpty(message = "Debes elegir un distrito*") String distrito) {
        this.distrito = distrito;
    }

    public @NotEmpty(message = "Debes ingresar tu direccion exacta") String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(@NotEmpty(message = "Debes ingresar tu direccion exacta") String direccionExacta) {
        this.direccionExacta = direccionExacta;
    }

    @Override
    public String toString() {
        return "DireccionDto{" +
                "provincia='" + provincia + '\'' +
                ", canton='" + canton + '\'' +
                ", distrito='" + distrito + '\'' +
                ", direccionExacta='" + direccionExacta + '\'' +
                '}';
    }
}
