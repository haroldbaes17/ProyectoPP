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
    private String direccionExacta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(String direccionExacta) {
        this.direccionExacta = direccionExacta;
    }
}
