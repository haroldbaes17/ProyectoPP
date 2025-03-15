package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.model.Detalle_pedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoRepository repository;

    // Para almacenar los detalles de la orden
    List<Detalle_pedido> detalles = new ArrayList<>();

    // Almacena los datos de la orden
    Pedido pedido = new Pedido();

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("productos", repository.findAll());

        return "usuario/home";
    }

    @GetMapping("/ver-producto")
    public String productoHome(@RequestParam int id, Model model) {

        Producto producto = new Producto();
        producto = repository.findById(id).get();

        model.addAttribute("producto", producto);

//        log.info("Producto: " + repository.findById(id).get());

        return "usuario/productoHome";
    }

    @PostMapping("/carrito")
    public String addCart(@RequestParam int id, @RequestParam int cantidad, Model model) {

        return "usuario/carrito";
    }


}
