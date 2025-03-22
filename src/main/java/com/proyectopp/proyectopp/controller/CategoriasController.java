package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/todas-las-camisetas")
    public String allProducts(
            Model model
    ) {

        List<Producto> productos = productoRepository.findAll();

        model.addAttribute("productos", productos);

        return "usuario/home";
    }

    @GetMapping("/camisetas-retro")
    public String camisasRetro(
            Model model
    ) {
        List<Producto> productosRetro = productoRepository.findAll().stream()
                .filter(Producto::isEsRetro)
                .collect(Collectors.toList());

        model.addAttribute("productos", productosRetro);
        return "usuario/home";
    }

    @GetMapping("/camisetas-edicion-especial")
    public String camisasEspecial(
            Model model
    ) {
        List<Producto> productosEspeciales = productoRepository.findAll().stream()
                .filter(Producto::isEdicionEspecial)
                .collect(Collectors.toList());

        model.addAttribute("productos", productosEspeciales);
        return "usuario/home";
    }

    @GetMapping("/selecciones")
    public String selecciones(
            Model model
    ) {
        List<Producto> selecciones = productoRepository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains("seleccion"))
                .collect(Collectors.toList());

        model.addAttribute("productos", selecciones);

        return "usuario/home";
    }

    @GetMapping("/clubes")
    public String clubes(
            Model model
    ) {
        List<Producto> clubes = productoRepository.findAll().stream()
                .filter(p -> !p.getNombre().toLowerCase().contains("seleccion"))
                .collect(Collectors.toList());

        model.addAttribute("productos", clubes);
        return "usuario/home";
    }
}
