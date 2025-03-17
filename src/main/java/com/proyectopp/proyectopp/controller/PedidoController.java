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
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PedidoController {

    private final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detalleRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Para almacenar los detalles del pedido
    List<DetallePedido> detalles = HomeController.detalles;

    @PostMapping("/procesar-pedido")
    public String savePedido(
            Model model,
            @Valid @ModelAttribute PedidoDto pedidoDto,
            @Valid @ModelAttribute DireccionDto direccionDto,
            @Valid @ModelAttribute UsuarioDto usuarioDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes

    ) {

//        log.info("PedidoDto: {}", pedidoDto);
//        for (DetallePedido detalle : detalles) {
//            System.out.println(detalle.toString());
//        }
//        log.info(direccionDto.toString());
//        log.info(usuarioDto.toString());

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

        detalles.clear();
        HomeController.detalles.clear();
        model.addAttribute("success", true);
        return "redirect:/pedido";
    }
}
