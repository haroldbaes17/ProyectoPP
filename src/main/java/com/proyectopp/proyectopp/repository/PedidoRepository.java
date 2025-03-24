package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioId(Integer usuarioId);

}
