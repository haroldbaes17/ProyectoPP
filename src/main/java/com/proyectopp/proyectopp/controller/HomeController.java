package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.CarritoDto;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Para almacenar los detalles del pedido
    List<DetallePedido> detalles = new ArrayList<>();

    // Almacena los datos del pedido
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
        CarritoDto carritoDto = new CarritoDto();
        carritoDto.setIdProducto(producto.getId());
        carritoDto.setPrecioUnitario(producto.getPrecio());
        model.addAttribute("carritoDto", carritoDto);
//        log.info("CarritoDto: " + carritoDto);

        model.addAttribute("producto", producto);


//        log.info("Producto: " + repository.findById(id).get());

        return "usuario/productoHome";
    }

    @GetMapping("/carrito")
    public String carritoHome(Model model) {

        // Calcular el total del pedido
        double sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                .sum();

        // Seteo de Pedido
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedido.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));

        model.addAttribute("carrito", detalles);
        model.addAttribute("pedido", pedido);

        return "usuario/carrito";
    }

    @PostMapping("/carrito")
    public String addCart(
            Model model,
            @Valid @ModelAttribute CarritoDto carritoDto,
            BindingResult result,
            RedirectAttributes redirectAttributes
            ) {

        Producto producto = repository.findById(carritoDto.getIdProducto()).get();
        DetallePedido detallePedido = new DetallePedido();
        double sumaTotal = 0;



        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            return "usuario/productoHome";
        }

        int precioUnitario = carritoDto.getPrecioUnitario().setScale(0, RoundingMode.HALF_UP).intValueExact();
        int cantidad = carritoDto.getCantidad();

        detallePedido.setProducto(producto);
        detallePedido.setTalla(carritoDto.getTalla());
        detallePedido.setCantidad(carritoDto.getCantidad());
        detallePedido.setPrecioUnitario(carritoDto.getPrecioUnitario());
        detallePedido.setSubtotal(BigDecimal.valueOf((long) precioUnitario * cantidad));

        // Verifica si el producto con la misma talla ya está en la lista
        boolean encontrado = false;
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId() == (producto.getId()) &&
                    detalle.getTalla().equals(carritoDto.getTalla())) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())));
                encontrado = true;
                break;
            }
        }

        // Si no se encontró, agregarlo como un nuevo elemento
        if (!encontrado) {
            detalles.add(detallePedido);


            sumaTotal = detalles.stream()
                    .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                    .sum();
        }


        //Seteos de Pedido

        //seteo de usuario
        //Seteo de direccion
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedido.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));

        model.addAttribute("carrito", detalles);
        model.addAttribute("pedido", pedido);

        return "usuario/carrito";
    }

    @GetMapping("/delete/cart")
    public String deleteProductoCart(
            @RequestParam int id,
            Model model
    ) {
        // Lista Nueva de productos en el carrito
        List<DetallePedido> pedidosNuevos = new ArrayList<>();

        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId() == id) {
                if (detalle.getCantidad() > 1) {
                    // Restar 1 a la cantidad del producto
                    detalle.setCantidad(detalle.getCantidad() - 1);
                    detalle.setSubtotal(detalle.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())));
                    pedidosNuevos.add(detalle);
                }
                // Si la cantidad es 1, el producto no se agrega a la nueva lista (se elimina)
            } else {
                pedidosNuevos.add(detalle);
            }
        }

        detalles = pedidosNuevos;

        // Calcular el total del pedido
        double sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                .sum();

        // Seteo de Pedido
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedido.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));

        model.addAttribute("carrito", detalles);
        model.addAttribute("pedido", pedido);

        return "usuario/carrito";
    }

    @GetMapping("/pedido")
    public String pedidoHome(Model model) {

        Usuario usuario = usuarioRepository.findById(2).get();

        // Calcular el total del pedido
        double sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                .sum();

        // Seteo de Pedido
        pedido.setFecha(LocalDate.now());
        pedido.setEstado("Pendiente");
        pedido.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedido.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));

        model.addAttribute("carrito", detalles);
        model.addAttribute("pedido", pedido);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }

}
