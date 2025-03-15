package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductoRepository repository;

    @GetMapping({"/", ""})
    public String home(Model model) {

        List<Producto> productos = repository.findAll();
        model.addAttribute("productos", productos);

        return "admin/home";
    }
}
