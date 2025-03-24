package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.model.StockTallas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTallasRepository extends JpaRepository<StockTallas, Integer> {

    List<StockTallas> findByProducto(Producto producto);
}
