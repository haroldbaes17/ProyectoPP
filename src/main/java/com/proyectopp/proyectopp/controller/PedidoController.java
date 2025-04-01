package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.DireccionDto;
import com.proyectopp.proyectopp.dto.PedidoDto;
import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Direccion;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.DireccionRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.EmailService;
import com.proyectopp.proyectopp.service.FacturaService;
import com.proyectopp.proyectopp.service.PedidoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
public class PedidoController {

    private final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    PedidoService pedidoService;

    @Autowired
    EmailService emailService;

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

    @CacheEvict(value = "historialPedidos", key = "#usuarioDto.id")
    @PostMapping("/procesar-pedido")
    public String savePedido(
            Model model,
            @Valid @ModelAttribute PedidoDto pedidoDto,
            @Valid @ModelAttribute DireccionDto direccionDto,
            @Valid @ModelAttribute UsuarioDto usuarioDto

    ) {

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

        emailService.sendOrderConfirmationEmail(usuarioDto.getCorreoElectronico(), "Confirmación de Pedido", pedido, direccion, usuario, detalles);
        model.addAttribute("success", true);

        HomeController.detalles.clear();
        return "redirect:/pedido";
    }
}
