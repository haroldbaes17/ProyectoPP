package com.proyectopp.proyectopp.component;

import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import com.proyectopp.proyectopp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreloadHistorialCache implements ApplicationRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(ApplicationArguments args) {
        // Puedes optar por precargar solo para ciertos usuarios o todos
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            // Llamamos al método para que se cachee el historial de cada usuario
            usuarioService.obtenerHistorialPedidos(usuario.getId());
        }
    }
}
