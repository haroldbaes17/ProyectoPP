package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.*;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.EmailService;
import com.proyectopp.proyectopp.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private UsuarioService service;

    @Autowired
    private EmailService emailService;

    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/mi-perfil")
    public String miPerfil(Model model, HttpSession session) {
        // 1) Obtenemos el usuario en sesión
        int idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario = repository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }

        // 2) Construimos el UsuarioDto para la vista
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellidos(usuario.getApellidos());
        usuarioDto.setCedula(usuario.getCedula());
        usuarioDto.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioDto.setTelefono(usuario.getTelefono());

        List<DetallePedido> itemsCarrito = HomeController.detalles;
        int cantidadCarrito = itemsCarrito.size();

        int cantidadPedidos = (int) pedidoRepository.countByUsuarioId(usuario.getId());

        model.addAttribute("direccionDto", new DireccionDto());
        model.addAttribute("cantidadPedidos", cantidadPedidos);
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute("cantidadCarrito", cantidadCarrito);

        return "usuario/profile";
    }

    @PostMapping("/actualizar-perfil")
    public String updateProfile(
            Model model,
            HttpSession session,
            @Valid @ModelAttribute UsuarioDto usuarioDto,
            @Valid @ModelAttribute DireccionDto direccionDto,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        Usuario newUser = repository.findById(
                Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        newUser.setNombre(usuarioDto.getNombre());
        newUser.setApellidos(usuarioDto.getApellidos());
        newUser.setCedula(usuarioDto.getCedula());
        newUser.setTelefono(usuarioDto.getTelefono());
        newUser.setCorreoElectronico(usuarioDto.getCorreoElectronico());

        repository.save(newUser);

        return "redirect:/mi-perfil";
    }

    @GetMapping("/historial")
    public String historial(Model model, HttpSession session) {
        int idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario = repository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }

        // Se obtiene el historial, que ya estará precargado en el caché
        List<PedidoDto> dtos = usuarioService.obtenerHistorialPedidos(usuario.getId());

        // Se filtran los DTOs por estado
        List<PedidoDto> pendientesDto = dtos.stream()
                .filter(dto -> "Pendiente".equals(dto.getEstado()))
                .collect(Collectors.toList());
        List<PedidoDto> enviadosDto = dtos.stream()
                .filter(dto -> "Enviado".equals(dto.getEstado()))
                .collect(Collectors.toList());
        List<PedidoDto> completadosDto = dtos.stream()
                .filter(dto -> "Entregado".equals(dto.getEstado()))
                .collect(Collectors.toList());

        model.addAttribute("pedidosPendientes", pendientesDto);
        model.addAttribute("pedidosEnviados", enviadosDto);
        model.addAttribute("pedidosCompletados", completadosDto);
        model.addAttribute("cantidadPedidos", dtos.size());

        return "usuario/historial";
    }

    @GetMapping("/admin/usuarios")
    public String showUsers(
            Model model
    ) {

        List<Usuario> usuarios = repository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios/show";
    }

    @GetMapping("/admin/usuarios/modificar")
    public String modificarUsuario(
            Model model,
            @RequestParam int id
    ) {
        Usuario usuario = repository.findById(id).orElse(null);

        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellidos(usuario.getApellidos());
        usuarioDto.setCedula(usuario.getCedula());
        usuarioDto.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioDto.setPassword(usuario.getPassword());
        usuarioDto.setTelefono(usuario.getTelefono());
        usuarioDto.setEs_admin(usuario.isEs_admin());
        usuarioDto.setConfirmado(usuario.isConfirmado());

        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/edit";
    }

}