package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.utils.TokenGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/registrarse")
    public String register(Model model) {
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute(usuarioDto);
        model.addAttribute("succes", false);

        return "auth/registrarse";
    }

    @PostMapping("/registrarse")
    public String register(
            Model model,
            @Valid @ModelAttribute UsuarioDto usuarioDto,
            BindingResult result) {

        Usuario usuario = repository.findByCorreoElectronico(usuarioDto.getCorreoElectronico());
        if (usuario != null) {
            result.addError (
                    new FieldError("usuarioDto", "correoElectronico", "El correo electronico ya existe")

            );
        }

        if (result.hasErrors()) {
            return "auth/registrarse";
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

        return "auth/registrarse";
    }
}
