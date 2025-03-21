package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.*;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.UsuarioService;
import com.proyectopp.proyectopp.utils.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrarse")
    public String register(Model model) {
        UsuarioDto usuarioDto = new UsuarioDto();

        model.addAttribute(usuarioDto);
        model.addAttribute("succes", false);

        return "auth/registro";
    }

    @PostMapping("/registrarse")
    public String register(
            Model model,
            @Valid @ModelAttribute UsuarioDto usuarioDto,
            BindingResult result
    ) {

//        log.info("Usuario Dto: {}", usuarioDto);

        Usuario usuario = repository.findByCorreoElectronico(usuarioDto.getCorreoElectronico());
        if (usuario != null) {
            result.addError (
                    new FieldError("usuarioDto", "correoElectronico", "El correo electronico ya existe")

            );
        }

        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmarPassword())) {
            result.addError (
                    new FieldError("usuarioDto", "password", "Las contraseñas no coinciden")
            );
        }
        if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmarPassword())) {
            result.addError (
                    new FieldError("usuarioDto", "confirmarPassword", "Las contraseñas no coinciden")
            );
        }

        if (result.hasErrors()) {
            return "auth/registro";
        }

        try {
            var bCryptEnconder = new BCryptPasswordEncoder();

            Usuario newUsuario = new Usuario();

            newUsuario.setNombre(usuarioDto.getNombre());
            newUsuario.setApellidos(usuarioDto.getApellidos());
            newUsuario.setCedula(usuarioDto.getCedula());
            newUsuario.setCorreoElectronico(usuarioDto.getCorreoElectronico());
            newUsuario.setPassword(bCryptEnconder.encode(usuarioDto.getPassword()));
            newUsuario.setTelefono(usuarioDto.getTelefono());
            newUsuario.setEs_admin(false);
            newUsuario.setConfirmado(false);
            newUsuario.setToken(TokenGenerator.generateShortToken());

            repository.save(newUsuario);

            model.addAttribute("usuarioDto", new UsuarioDto());
            model.addAttribute("succes", true);

        } catch (Exception e) {
            result.addError (
                    new FieldError("usuarioDto", "nombre", e.getMessage())
            );
        }
        model.addAttribute("succes", true);

        return "auth/registro";
    }

    @GetMapping("/login")
    public String login(
            Model model
    ) {
        LoginDto loginDto = new LoginDto();
        model.addAttribute(loginDto);

        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginDto loginDto,
            HttpSession session,
            HttpServletRequest request,
            BindingResult result
    ){

        var bCryptEnconder = new BCryptPasswordEncoder();

        Usuario usuario = repository.findByCorreoElectronico(loginDto.getCorreoElectronico());

        if (usuario == null) {
            result.addError (
                    new FieldError("loginDto", "correoElectronico", "No encontramos tu usuario")
            );
            return "auth/login";
        }

        if (!bCryptEnconder.matches(loginDto.getPassword(), usuario.getPassword())) {
            result.addError (
                    new FieldError("loginDto", "password", "Contraseña incorrecta")
            );
        }

        if (result.hasErrors()) {
            return "auth/login";
        }

        session.setAttribute("idUsuario", usuario.getId());

        // Crear la autenticación
        UserDetails userDetails = usuarioService.loadUserByUsername(usuario.getCorreoElectronico());
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Persistir el SecurityContext en la sesión para que persista en siguientes peticiones
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());


        if (usuario.isEs_admin()) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        HomeController.detalles.clear();
        return "redirect:/";
    }

    @GetMapping("/mi-perfil")
    public String miPerfil(Model model, HttpSession session) {

        // 1) Obtenemos el usuario en sesión
        int idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario = repository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            // Manejo de error, por ejemplo redirigir o mostrar un mensaje
            return "redirect:/login";
        }

        // 2) Construimos el UsuarioDto para la vista
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellidos(usuario.getApellidos());
        usuarioDto.setCedula(usuario.getCedula());
        usuarioDto.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioDto.setTelefono(usuario.getTelefono());

        // 3) Calculamos la cantidad de productos en el carrito
        List<DetallePedido> itemsCarrito = HomeController.detalles;  // Ejemplo tuyo
        int cantidadCarrito = itemsCarrito.size();

        // 4) Buscamos únicamente los pedidos del usuario actual
        List<Pedido> pedidosUsuario = pedidoRepository.findByUsuarioId(usuario.getId());


        // 8) Agregamos los datos al modelo

        model.addAttribute("direccionDto", new DireccionDto());
        model.addAttribute("cantidadPedidos", pedidosUsuario.size());
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
    public String historial(
            Model model, HttpSession session
    ) {

        // 1) Obtenemos el usuario en sesión
        int idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
        Usuario usuario = repository.findById(idUsuario).orElse(null);

        if (usuario == null) {
            // Manejo de error, por ejemplo redirigir o mostrar un mensaje
            return "redirect:/login";
        }
        // 4) Buscamos únicamente los pedidos del usuario actual
        List<Pedido> pedidosUsuario = pedidoRepository.findByUsuarioId(usuario.getId());

        // 5) Obtenemos los IDs de los pedidos y buscamos solo los detalles correspondientes
        List<Integer> pedidoIds = pedidosUsuario.stream()
                .map(Pedido::getId)
                .collect(Collectors.toList());
        List<DetallePedido> detallesPedidos = pedidoIds.isEmpty()
                ? new ArrayList<>()
                : detallePedidoRepository.findByPedidoIdIn(pedidoIds);

        // 6) Separamos en pedidos pendientes y completados usando stream API
        List<Pedido> pedidosPendientes = pedidosUsuario.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Pendiente")
                        || p.getEstado().equalsIgnoreCase("Enviado"))
                .collect(Collectors.toList());

        List<Pedido> pedidosCompletados = pedidosUsuario.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase("Entregado"))
                .collect(Collectors.toList());

        // 7) Convertimos cada Pedido a PedidoDto, asignándole los detalles correctos
        List<PedidoDto> pedidosPendientesDto = usuarioService.construirPedidoDtos(pedidosPendientes, detallesPedidos);
        List<PedidoDto> pedidosCompletadosDto = usuarioService.construirPedidoDtos(pedidosCompletados, detallesPedidos);

        // 8) Agregamos los datos al modelo
        model.addAttribute("pedidosPendientes", pedidosPendientesDto);
        model.addAttribute("pedidosCompletados", pedidosCompletadosDto);
        model.addAttribute("cantidadPedidos", pedidosUsuario.size());


        return "usuario/historial";
    }


}