package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stock_tallas")
public class StockTallas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "talla", length = 5, nullable = false)
    private String talla;

    @Column(name = "cantidad", columnDefinition = "TINYINT(6)",nullable = false)
    private int cantidad;

    public StockTallas(String talla, int cantidad) {
    }

    public StockTallas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
