package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findByPedidoIdIn(List<Integer> pedidoIds);
}
