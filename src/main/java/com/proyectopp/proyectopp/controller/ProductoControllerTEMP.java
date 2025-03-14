//package com.proyectopp.proyectopp.controller;
//
//import com.proyectopp.proyectopp.dto.ProductoDto;
//import com.proyectopp.proyectopp.model.Producto;
//import com.proyectopp.proyectopp.repository.ProductoRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping("/productos")
//public class ProductoControllerTEMP {
//
//    @Autowired
//    private ProductoRepository repository;
//
//    @GetMapping({"", "/"})
//    public String showProductList(Model model) {
//        List<Producto> productos = repository.findAll();
//        model.addAttribute("productos", productos);
//        return "productos/index";
//    }
//
//    @GetMapping("/crear")
//    public String showCreatePage(Model model) {
//        ProductoDto productoDto = new ProductoDto();
//        model.addAttribute("productoDto", productoDto);
//        return "productos/CrearProducto";
//    }
//
//    @PostMapping("/crear")
//    public String createProduct(@Valid @ModelAttribute ProductoDto productoDto, BindingResult result) {
//
//        if (productoDto.getImagen().isEmpty()) {
//            result.addError(new FieldError("productoDto", "imagen", "La imagen es requerida*"));
//        }
//
//        if (result.hasErrors()) {
//            return "productos/CrearProducto";
//        }
//
//        //Guardar la imagen en el servidor
//        MultipartFile imagen = productoDto.getImagen();
//        Date createdAt = new Date();
//        String storageFileName = createdAt.getTime() + "_" + imagen.getOriginalFilename();
//
//        try {
//            String uploadDir = "public/images/";
//            Path uploadPath = Paths.get(uploadDir);
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            try (InputStream inputStream = imagen.getInputStream()) {
//                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
//                        StandardCopyOption.REPLACE_EXISTING);
//            }
//        }catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//
//        Producto producto = new Producto();
//        producto.setNombre(productoDto.getNombre());
//        producto.setEquipo(productoDto.getEquipo());
//        producto.setPrecio(productoDto.getPrecio());
//        producto.setImagen(storageFileName);
//        producto.setStock_total(productoDto.getStock_total());
//        producto.setTipo_equipacion(productoDto.getTipo_equipacion());
//        producto.setLiga(productoDto.getLiga());
//        producto.setPais(productoDto.getPais());
//        producto.setTemporada(productoDto.getTemporada());
//        producto.setEdicion_especial(productoDto.getEdicion_especial());
//        producto.setEs_retro(productoDto.getEs_retro());
//        producto.setDescripcion(productoDto.getDescripcion());
//
//        repository.save(producto);
//
//        return "redirect:/productos";
//    }
//
//    @GetMapping("/modificar")
//    public String showUpdatePage(Model model, @RequestParam int id) {
//
//        try {
//            Producto producto = repository.findById(id).get();
//            model.addAttribute("producto", producto);
//
//            ProductoDto productoDto = new ProductoDto();
//            productoDto.setNombre(producto.getNombre());
//            productoDto.setEquipo(producto.getEquipo());
//            productoDto.setPrecio(producto.getPrecio());
//            productoDto.setStock_total(producto.getStock_total());
//            productoDto.setTipo_equipacion(producto.getTipo_equipacion());
//            productoDto.setLiga(producto.getLiga());
//            productoDto.setPais(producto.getPais());
//            productoDto.setTemporada(producto.getTemporada());
//            productoDto.setEdicion_especial(producto.isEdicion_especial());
//            productoDto.setEs_retro(producto.isEs_retro());
//            productoDto.setDescripcion(producto.getDescripcion());
//
//            model.addAttribute("productoDto", productoDto);
//
//
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//            return "redirect:/productos";
//        }
//
//        return "productos/ModificarProducto";
//    }
//
//    @PostMapping("/modificar")
//    public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductoDto productoDto, BindingResult result) {
//
//        try {
//            Producto producto = repository.findById(id).get();
//            model.addAttribute("producto", producto);
//
//            if (result.hasErrors()) {
//                return "productos/ModificarProducto";
//            }
//
//            if (!productoDto.getImagen().isEmpty()) {
//                //Delete old image
//                String uploadDir = "public/images/";
//                Path oldImagePath = Paths.get(uploadDir + producto.getImagen());
//
//                try {
//                    Files.delete(oldImagePath);
//                } catch (Exception e) {
//                    System.out.println("Exception: " + e.getMessage());
//                }
//
//                //Save a new image file
//                MultipartFile imagen = productoDto.getImagen();
//                Date createdAt = new Date();
//                String storageFileName = createdAt.getTime() + "_" + imagen.getOriginalFilename();
//
//                try (InputStream inputStream = imagen.getInputStream()) {
//                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
//                            StandardCopyOption.REPLACE_EXISTING);
//                }
//                producto.setImagen(storageFileName);
//            }
//
//            producto.setNombre(productoDto.getNombre());
//            producto.setEquipo(productoDto.getEquipo());
//            producto.setPrecio(productoDto.getPrecio());
//            producto.setStock_total(productoDto.getStock_total());
//            producto.setTipo_equipacion(productoDto.getTipo_equipacion());
//            producto.setLiga(productoDto.getLiga());
//            producto.setPais(productoDto.getPais());
//            producto.setTemporada(productoDto.getTemporada());
//            producto.setEdicion_especial(productoDto.getEdicion_especial());
//            producto.setEs_retro(productoDto.getEs_retro());
//            producto.setDescripcion(productoDto.getDescripcion());
//
//            repository.save(producto);
//
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//
//        return "redirect:/productos/";
//    }
//
//    @GetMapping("/eliminar")
//    public String deleteProduct(@RequestParam int id) {
//
//        try {
//            Producto producto = repository.findById(id).get();
//
//            //Delete product image
//            Path imagePath = Paths.get("public/images/" + producto.getImagen());
//
//            try {
//                Files.delete(imagePath);
//            } catch (Exception e) {
//                System.out.println("Exception: " + e.getMessage());
//            }
//            repository.delete(producto);
//
//        } catch (Exception e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//
//        return "redirect:/productos";
//    }
//
//}