package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.controller.HomeController;
import com.proyectopp.proyectopp.dto.DireccionDto;
import com.proyectopp.proyectopp.dto.PedidoDto;
import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.*;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.DireccionRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detalleRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FacturaService facturaService;

    public void guardarPedido(PedidoDto pedidoDto, DireccionDto direccionDto, UsuarioDto usuarioDto) {
        List<DetallePedido> detalles = HomeController.detalles;

        Direccion direccion = new Direccion();
        Pedido pedido = new Pedido();
        Usuario usuario = usuarioRepository.findById(usuarioDto.getId()).get();

        //Seteos de Direccion
        direccion.setUsuario(usuario);
        direccion.setProvincia(direccionDto.getProvincia());
        direccion.setCanton(direccionDto.getCanton());
        direccion.setDistrito(direccionDto.getDistrito());
        direccion.setDireccionExacta(direccionDto.getDireccionExacta());

        direccionRepository.save(direccion);

        //Seteos de Pedido
        pedido.setUsuario(usuario);
        pedido.setDireccion(direccion);
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setSubtotal(pedidoDto.getSubtotal());
        pedido.setTotal(pedidoDto.getTotal());

        pedidoRepository.save(pedido);

        //Seteos de DetallePedido
        for (DetallePedido dt : detalles) {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(dt.getProducto());
            detallePedido.setTalla(dt.getTalla());
            detallePedido.setCantidad(dt.getCantidad());
            detallePedido.setPrecioUnitario(dt.getPrecioUnitario());
            detallePedido.setSubtotal(dt.getSubtotal());

            detalleRepository.save(detallePedido);
        }

        facturaService.guardarFactura(pedido, usuario);

    }
}
