package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByCorreoElectronico(String correo_electronico);
}
