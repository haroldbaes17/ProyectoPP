package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.LoginDto;
import com.proyectopp.proyectopp.dto.UsuarioDto;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.EmailService;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmailService emailService;

    Logger log = LoggerFactory.getLogger(AuthController.class);

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

            // Generar el enlace de verificación (ajusta el dominio y puerto según corresponda)
            String confirmationLink = "http://localhost:8080/verificar-cuenta?token=" + newUsuario.getToken();
            emailService.sendConfirmatioEmail(newUsuario.getCorreoElectronico(), "Confirmación de cuenta", confirmationLink);

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

        // Verificar si la cuenta está confirmada
        if (!usuario.isConfirmado()) {
            result.addError(new ObjectError("loginDto", "Tu cuenta no ha sido confirmada. Por favor, revisa tu correo electrónico para activar tu cuenta."));
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

    @GetMapping("/mensaje")
    public String mensaje() {

        return "auth/mensaje";
    }

    @GetMapping("/verificar-cuenta")
    public String verificarCuenta(
            Model model,
            @RequestParam String token,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = repository.findByToken(token);

        if (usuario != null) {
            usuario.setConfirmado(true);
            usuario.setToken(null);
            repository.save(usuario);
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("error", "true");
        return "redirect:/error";

    }

    @PostMapping("/olvide-password")
    public String olvidePassword(
            Model model,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute LoginDto loginDto,
            BindingResult result
    ){

        Usuario usuario = repository.findByCorreoElectronico(loginDto.getCorreoElectronico());

        if (usuario == null) {
            result.addError (
                    new FieldError("loginDto", "correoElectronico", "No existe una cuenta asociada a ese correo.")
            );
            return "auth/perdiPassword";
        }
        String token = TokenGenerator.generateShortToken();
        usuario.setToken(token);
        repository.save(usuario);

        String changePasswordLink = "http://localhost:8080/reestablecer-password?token=" + token;

        emailService.sendResetPasswordEmail(usuario.getCorreoElectronico(), "Cambio de Contraseña", changePasswordLink);

        redirectAttributes.addFlashAttribute("succes", true );
        return "redirect:/olvide-password";
    }

    @GetMapping("/reestablecer-password")
    public String reestablecerPassword(
            Model model,
            @RequestParam String token
    ) {
        LoginDto loginDto = new LoginDto();

        Usuario usuario = repository.findByToken(token);

        if (usuario == null) {
            return "redirect:/error";
        }

        loginDto.setCorreoElectronico(usuario.getCorreoElectronico());

        model.addAttribute(loginDto);
        return "auth/reestablecer-password";
    }

    @PostMapping("/reestablecer-password")
    public String cambiarPassword(
            Model model,
            @Valid @ModelAttribute LoginDto loginDto,
            RedirectAttributes redirectAttributes,
            BindingResult result
    ) {

        var bCryptEnconder = new BCryptPasswordEncoder();

        Usuario usuario = repository.findByCorreoElectronico(loginDto.getCorreoElectronico());

        if (loginDto.getPassword().length() < 8) {
            result.addError (
                    new FieldError("usuarioDto", "password", "El tamaño de la contraseña no debe ser menor a 8 caracteres")
            );
            return "auth/reestablecer-password";
        }

        if (!loginDto.getPassword().equals(loginDto.getConfirmarPassword())) {
            result.addError (
                    new FieldError("usuarioDto", "password", "Las contraseñas no coinciden")
            );
            result.addError (
                    new FieldError("usuarioDto", "confirmarPassword", "Las contraseñas no coinciden")
            );
            return "auth/reestablecer-password";
        }

        if (result.hasErrors()) {
            return "auth/reestablecer-password";
        }

        String password = loginDto.getPassword();

        usuario.setPassword(bCryptEnconder.encode(password));
        usuario.setToken(null);
        repository.save(usuario);

        emailService.sendPasswordChangedNotification(usuario.getCorreoElectronico(), "Cambio de Contraseña Exitoso");

        redirectAttributes.addFlashAttribute("succesPassword", true);
        return "redirect:/login?passwordChanged=true";
    }
}
