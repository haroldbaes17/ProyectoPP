package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {



    // Obtiene todos los pedidos de un usuario
    List<Pedido> findByUsuarioId(Integer usuarioId);

    // Obtiene los pedidos de un usuario filtrados por estado
    List<Pedido> findByUsuarioIdAndEstado(Integer usuarioId, String estado);

    long countByUsuarioId(int usuarioId);


}
