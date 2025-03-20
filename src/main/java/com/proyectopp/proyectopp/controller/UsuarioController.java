package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.LoginDto;
import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.UsuarioService;
import com.proyectopp.proyectopp.utils.TokenGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

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
    public String miPerfil(Model model) {

        return "usuario/profile";
    }
}