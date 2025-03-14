package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.model.Producto;

import java.util.Optional;

public interface ProductoService {
    public Producto save(Producto producto);
    public Optional<Producto> get(int id);
    public void update(Producto producto);
    public void delete(int id);
}
