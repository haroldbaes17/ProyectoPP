package com.proyectopp.proyectopp.dto;

import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Direccion;
import com.proyectopp.proyectopp.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoDto {

    private Usuario usuario;
    private Direccion direccion;
    private LocalDate fecha;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal total;
    private List<DetallePedido> detalles;



    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "PedidoDto{" +
                "usuario=" + usuario +
                ", direccion=" + direccion +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", detalles=" + detalles +
                '}';
    }
}
