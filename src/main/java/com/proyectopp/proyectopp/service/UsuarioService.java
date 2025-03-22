
package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.dto.PedidoDto;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email);

        if (usuario != null) {
            String role = usuario.isEs_admin() ? "ADMIN" : "USER";
            return User.withUsername(usuario.getCorreoElectronico())
                    .password(usuario.getPassword())
                    .roles(role)
                    .build();
        }

        return null;
    }

    /**
     * Método de ayuda para convertir una lista de entidades Pedido a una lista de PedidoDto,
     * asignando sus DetallePedido correspondientes.
     */
    public List<PedidoDto> construirPedidoDtos(List<Pedido> pedidos, List<DetallePedido> todosLosDetalles) {
        List<PedidoDto> resultado = new ArrayList<>();

        for (Pedido p : pedidos) {
            // Creamos un nuevo DTO
            PedidoDto dto = new PedidoDto();
            dto.setUsuario(p.getUsuario());
            dto.setDireccion(p.getDireccion());
            dto.setFecha(p.getFecha());
            dto.setEstado(p.getEstado());
            dto.setSubtotal(p.getSubtotal());
            dto.setTotal(p.getTotal());

            // Filtramos los detalles que pertenecen a este pedido
            List<DetallePedido> detallesDeEstePedido = new ArrayList<>();
            for (DetallePedido d : todosLosDetalles) {
                if (d.getPedido().getId() == (p.getId())) {
                    detallesDeEstePedido.add(d);
                }
            }
            dto.setDetalles(detallesDeEstePedido);

            // Agregamos el DTO a la lista
            resultado.add(dto);
        }

        return resultado;
    }

}
