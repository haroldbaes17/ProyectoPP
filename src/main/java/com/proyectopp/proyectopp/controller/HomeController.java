package com.proyectopp.proyectopp.controller;

import com.proyectopp.proyectopp.dto.*;
import com.proyectopp.proyectopp.model.DetallePedido;
import com.proyectopp.proyectopp.model.Pedido;
import com.proyectopp.proyectopp.model.Producto;
import com.proyectopp.proyectopp.model.Usuario;
import com.proyectopp.proyectopp.repository.ProductoRepository;
import com.proyectopp.proyectopp.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
     private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Para almacenar los detalles del pedido
    public static List<DetallePedido> detalles = new ArrayList<>();

    // Almacena los datos del pedido
    Pedido pedido = new Pedido();
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("")
    public String home(Model model, HttpSession session) {
        model.addAttribute("productos", repository.findAll());
//        log.info("session: " + session.getAttribute("idUsuario"));

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
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedidoDto.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));
        pedidoDto.setDetalles(detalles);

        model.addAttribute("pedidoDto", pedidoDto);

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
        }
        sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                .sum();

        //Seteos de PedidoDto
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedidoDto.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));
        pedidoDto.setDetalles(detalles);

        model.addAttribute("pedidoDto", pedidoDto);

        return "usuario/carrito";
    }

    //Recibir Dto
    @GetMapping("/delete-from-cart")
    public String deleteProductoCart(
            @RequestParam int id,
            @RequestParam String talla,
            Model model
    ) {
        // Lista Nueva de productos en el carrito
        List<DetallePedido> pedidosNuevos = new ArrayList<>();

        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId() == id && detalle.getTalla().equalsIgnoreCase(talla)) {
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

        // Seteo de PedidoDto
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedidoDto.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));
        pedidoDto.setDetalles(detalles);

        model.addAttribute("pedidoDto", pedidoDto);

        return "usuario/carrito";
    }


    //Crear Dtos
    @GetMapping("/pedido")
    public String pedidoHome(Model model, HttpSession session) {

        PedidoDto pedidoDto = new PedidoDto();
        DireccionDto direccionDto = new DireccionDto();
        UsuarioDto usuarioDto = new UsuarioDto();

        Usuario usuario = usuarioRepository.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();


        // Calcular el total del pedido
        double sumaTotal = detalles.stream()
                .mapToDouble(dt -> dt.getSubtotal().doubleValue())
                .sum();

        //Seteos UsuarioDto
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setApellidos(usuario.getApellidos());
        usuarioDto.setCedula(usuario.getCedula());
        usuarioDto.setCorreoElectronico(usuario.getCorreoElectronico());
        usuarioDto.setTelefono(usuario.getTelefono());

        //Setetos PedidoDto
        pedidoDto.setSubtotal(BigDecimal.valueOf(sumaTotal));
        pedidoDto.setTotal(BigDecimal.valueOf(sumaTotal + sumaTotal * 0.13));
        pedidoDto.setDetalles(detalles);

        model.addAttribute("pedidoDto", pedidoDto);
        model.addAttribute("usuarioDto", usuarioDto);
        model.addAttribute("direccionDto", direccionDto);

        model.addAttribute("detalles", detalles);

        return "usuario/resumenorden";
    }

    @PostMapping("/buscar-producto")
    public String search(
            Model model,
            @RequestParam String nombre
    ) {

//        log.info("Nombre: " + nombre);

        List<Producto> productos = productoRepository.findAll().stream()
                .filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());

        model.addAttribute("productos", productos);
        return "usuario/home";
    }

}
