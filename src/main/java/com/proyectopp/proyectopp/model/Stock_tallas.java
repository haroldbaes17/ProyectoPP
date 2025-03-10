package com.proyectopp.proyectopp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stock_tallas")
public class Stock_tallas {
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
}
