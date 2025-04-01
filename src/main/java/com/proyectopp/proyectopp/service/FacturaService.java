package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.controller.HomeController;
import com.proyectopp.proyectopp.model.*;
import com.proyectopp.proyectopp.repository.DetalleFacturaRepository;
import com.proyectopp.proyectopp.repository.FacturaRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public void guardarFactura(Pedido pedido, Usuario usuario) {
        List<DetallePedido> detalles = HomeController.detalles;

        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setUsuario(usuario);
        factura.setFecha(LocalDate.now());
        factura.setTotal(pedido.getTotal());

        facturaRepository.save(factura);

        //Detalle Factura
        for (DetallePedido dt : detalles) {
            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setFactura(factura);
            detalleFactura.setProducto(dt.getProducto());
            detalleFactura.setTalla(dt.getTalla());
            detalleFactura.setCantidad(dt.getCantidad());
            detalleFactura.setPrecioUnitario(dt.getPrecioUnitario());
            detalleFactura.setSubtotal(dt.getSubtotal());

            detalleFacturaRepository.save(detalleFactura);
        }


    }
}
