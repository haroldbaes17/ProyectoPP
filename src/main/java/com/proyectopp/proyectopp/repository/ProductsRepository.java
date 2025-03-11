package com.proyectopp.proyectopp.repository;

import com.proyectopp.proyectopp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Producto, Integer> {

}
