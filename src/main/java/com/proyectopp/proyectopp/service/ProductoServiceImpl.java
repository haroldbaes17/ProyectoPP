package com.proyectopp.proyectopp.service;

import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Override
    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    @Override
    public Optional<Producto> get(int id) {
        return repository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        repository.save(producto);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
