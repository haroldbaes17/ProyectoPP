package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.ProductoDto;
import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoRepository repository;

    @GetMapping({"/", ""})
    public String show(
            Model model
    ) {
        List<Producto> productos = repository.findAll();
        model.addAttribute("productos", productos);
        return "admin/productos/show";
    }

    @GetMapping("/crear")
    public String create(Model model) {
        ProductoDto productoDto = new ProductoDto();
        model.addAttribute("productoDto", productoDto);
        return "/admin/productos/create";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute ProductoDto productoDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
//        LOGGER.info("Este es el objeto producto {}", productoDto);

        if(productoDto.getImagen().isEmpty()) {
            result.addError(new FieldError("productoDto", "imagen", "La imagen es requerida"));
        }
        if (result.hasErrors()) {
            return "/admin/productos/create";
        }

        //Guardar la imagen en el servidor
        MultipartFile imagen = productoDto.getImagen();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + imagen.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imagen.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setEquipo(productoDto.getEquipo());
        producto.setPrecio(productoDto.getPrecio());
        producto.setImagen(storageFileName);
        producto.setStockTotal(productoDto.getStockTotal());
        producto.setTipoEquipacion(productoDto.getTipoEquipacion());
        producto.setLiga(productoDto.getLiga());
        producto.setPais(productoDto.getPais());
        producto.setTemporada(productoDto.getTemporada());
        producto.setEdicionEspecial(productoDto.getEdicionEspecial());
        producto.setEsRetro(productoDto.getEsRetro());
        producto.setDescripcion(productoDto.getDescripcion());

        repository.save(producto);
        redirectAttributes.addFlashAttribute("successCreate", true);

        return "redirect:/admin/productos";
    }

    @GetMapping("/modificar")
    public String modificar(Model model, @RequestParam int id) {

        try {
            Producto producto = repository.findById(id).get();
            model.addAttribute("producto", producto);
//            LOGGER.info("Producto Buscado: {}", producto);

            ProductoDto productoDto = new ProductoDto();
            productoDto.setNombre(producto.getNombre());
            productoDto.setEquipo(producto.getEquipo());
            productoDto.setPrecio(producto.getPrecio());
            productoDto.setStockTotal(producto.getStockTotal());
            productoDto.setTipoEquipacion(producto.getTipoEquipacion());
            productoDto.setLiga(producto.getLiga());
            productoDto.setPais(producto.getPais());
            productoDto.setTemporada(producto.getTemporada());
            productoDto.setEdicionEspecial(producto.isEdicionEspecial());
            productoDto.setEsRetro(producto.isEsRetro());
            productoDto.setDescripcion(producto.getDescripcion());

            model.addAttribute("productoDto", productoDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/admin/productos/edit";
        }

        return "admin/productos/edit";
    }

    @PostMapping("/modificar")
    public String edit(Model model, @RequestParam int id, @Valid @ModelAttribute ProductoDto productoDto, BindingResult result, RedirectAttributes redirectAttributes) {

        try {
            Producto producto = repository.findById(id).get();
            model.addAttribute("producto", producto);
            LOGGER.info("Producto Buscado: {}", productoDto);

            if (result.hasErrors()) {
                return "/admin/productos/edit";
            }

            if (!productoDto.getImagen().isEmpty()) {
                //Delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + producto.getImagen());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //Save a new image file
                MultipartFile imagen = productoDto.getImagen();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + imagen.getOriginalFilename();

                try (InputStream inputStream = imagen.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                producto.setImagen(storageFileName);
            }

            producto.setNombre(productoDto.getNombre());
            producto.setEquipo(productoDto.getEquipo());
            producto.setPrecio(productoDto.getPrecio());
            producto.setStockTotal(productoDto.getStockTotal());
            producto.setTipoEquipacion(productoDto.getTipoEquipacion());
            producto.setLiga(productoDto.getLiga());
            producto.setPais(productoDto.getPais());
            producto.setTemporada(productoDto.getTemporada());
            producto.setEdicionEspecial(productoDto.getEdicionEspecial());
            producto.setEsRetro(productoDto.getEsRetro());
            producto.setDescripcion(productoDto.getDescripcion());

            repository.save(producto);
            redirectAttributes.addFlashAttribute("successUpdate", true);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminar")
    public String delete(
            @RequestParam int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Producto producto = repository.findById(id).get();

            //Delete product image
            Path imagePath = Paths.get("public/images/" + producto.getImagen());

            try {
                Files.delete(imagePath);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
            repository.delete(producto);
            redirectAttributes.addFlashAttribute("successDelete", true);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/admin/productos";
    }
}
