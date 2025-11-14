package com.dragoncoredev.gestion_pedidos_api.controller;

// 1. Importamos el DTO que vamos a RECIBIR
import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO;
// 2. Importamos la Entidad
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
// 3. Importamos el Servicio
import com.dragoncoredev.gestion_pedidos_api.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
// 4. ¡Importamos las NUEVAS anotaciones!
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // <-- ¡NUEVA!
import org.springframework.web.bind.annotation.RequestBody; // <-- ¡NUEVA!
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus; // <-- ¡NUEVA!
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para gestionar las peticiones relacionadas con Productos.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Endpoint para obtener todos los productos.
     * Responde a peticiones GET en /api/productos
     */
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    // --- ¡NUESTRO ENDPOINT POST! ---

    /**
     * Endpoint para crear un nuevo producto.
     * Responde a peticiones POST en /api/productos
     *
     * @param productoDTO El DTO (en formato JSON) que viene en el cuerpo de la petición.
     * @return El Producto entidad que ha sido creado (con su ID) en formato JSON.
     */
    @PostMapping // (1) Mapea este método a peticiones HTTP POST
    @ResponseStatus(HttpStatus.CREATED) // (2) Devuelve un código HTTP 201 (Created)
    public Producto crearProducto(
            @RequestBody CrearProductoDTO productoDTO // (3) Mapea el JSON del body a nuestro DTO
    ) {
        // (4) El controlador es "tonto". Solo llama al servicio
        //     y le pasa el DTO para que haga el trabajo.
        return productoService.crearProducto(productoDTO);
    }
}