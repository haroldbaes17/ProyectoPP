package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.DireccionDto;
import com.proyectopp.proyectopp.dto.PedidoDto;
import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.PedidoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PedidoController {

    private final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    PedidoService pedidoService;

    @PostMapping("/procesar-pedido")
    public String savePedido(
            Model model,
            @Valid @ModelAttribute PedidoDto pedidoDto,
            @Valid @ModelAttribute DireccionDto direccionDto,
            @Valid @ModelAttribute UsuarioDto usuarioDto

    ) {
        pedidoService.guardarPedido(pedidoDto, direccionDto, usuarioDto);
        model.addAttribute("success", true);
        return "redirect:/pedido";
    }
}
