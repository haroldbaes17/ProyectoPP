package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    @Query(value = "SELECT * FROM u858550527_ProyectoPP.productos ORDER BY RAND() LIMIT 8", nativeQuery = true)
    List<Producto> findRandomProductos();

}
