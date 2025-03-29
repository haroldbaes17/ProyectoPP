
package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.dto.PedidoDto;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.DetallePedidoRepository;
import com.proyectopp.proyectopp.repository.PedidoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

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

    @Cacheable(value = "historialPedidos", key = "#usuarioId")
    public List<PedidoDto> obtenerHistorialPedidos(Integer usuarioId) {
        // Se realizan las consultas y se construyen los DTOs (puedes usar fetch join o tu lógica actual)
        List<Pedido> pedidosPendientes = pedidoRepository.findByUsuarioIdAndEstado(usuarioId, "Pendiente");
        List<Pedido> pedidosEnviados   = pedidoRepository.findByUsuarioIdAndEstado(usuarioId, "Enviado");
        List<Pedido> pedidosCompletados = pedidoRepository.findByUsuarioIdAndEstado(usuarioId, "Entregado");

        List<Integer> pedidoIds = Stream.concat(
                        Stream.concat(pedidosPendientes.stream(), pedidosEnviados.stream()),
                        pedidosCompletados.stream()
                )
                .map(Pedido::getId)
                .distinct()
                .collect(Collectors.toList());

        List<DetallePedido> detallesPedidos = pedidoIds.isEmpty() ?
                new ArrayList<>() : detallePedidoRepository.findByPedidoIdIn(pedidoIds);

        // Construcción de DTOs (utilizando tu método o lógica)
        List<PedidoDto> pendientesDto = construirPedidoDtos(pedidosPendientes, detallesPedidos);
        List<PedidoDto> enviadosDto   = construirPedidoDtos(pedidosEnviados, detallesPedidos);
        List<PedidoDto> completadosDto = construirPedidoDtos(pedidosCompletados, detallesPedidos);

        // Aquí podrías decidir retornar todos los DTOs juntos o encapsularlos en otro objeto.
        // Por simplicidad, retornamos una lista combinada.
        List<PedidoDto> dtos = new ArrayList<>();
        dtos.addAll(pendientesDto);
        dtos.addAll(enviadosDto);
        dtos.addAll(completadosDto);

        return dtos;
    }

    public List<PedidoDto> construirPedidoDtos(List<Pedido> pedidos, List<DetallePedido> todosLosDetalles) {
        Map<Integer, List<DetallePedido>> detallesPorPedido = new HashMap<>();
        for (DetallePedido detalle : todosLosDetalles) {
            int pedidoId = detalle.getPedido().getId();
            detallesPorPedido.computeIfAbsent(pedidoId, k -> new ArrayList<>()).add(detalle);
        }

        List<PedidoDto> resultado = new ArrayList<>(pedidos.size());
        for (Pedido pedido : pedidos) {
            PedidoDto dto = new PedidoDto();
            dto.setUsuario(pedido.getUsuario());
            dto.setDireccion(pedido.getDireccion());
            dto.setFecha(pedido.getFecha());
            dto.setEstado(pedido.getEstado());
            dto.setSubtotal(pedido.getSubtotal());
            dto.setTotal(pedido.getTotal());
            dto.setDetalles(detallesPorPedido.getOrDefault(pedido.getId(), new ArrayList<>()));
            resultado.add(dto);
        }
        return resultado;
    }




}
