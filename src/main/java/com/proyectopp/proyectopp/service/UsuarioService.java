
package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email);

        if (usuario != null) {
            var SpringUser = User.withUsername(usuario.getCorreoElectronico())
                    .password(usuario.getPassword())
                    .roles(usuario.isEs_admin() ? "Admin" : "Usuario")
                    .build();

            return SpringUser;
        }

        return null;
    }

}
