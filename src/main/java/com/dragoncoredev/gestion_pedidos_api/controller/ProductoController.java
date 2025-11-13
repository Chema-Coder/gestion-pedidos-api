package com.dragoncoredev.gestion_pedidos_api.controller;

// 1. Importamos el "plano" (la entidad/DTO que vamos a devolver)
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
// 2. Importamos el "cerebro" (el servicio que vamos a usar)
import com.dragoncoredev.gestion_pedidos_api.service.ProductoService;

// 3. Importamos la "inyección"
import org.springframework.beans.factory.annotation.Autowired;
// 4. Importamos las anotaciones web de Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; // Necesitamos esto para devolver una lista

/**
 * Controlador REST para gestionar las peticiones relacionadas con Productos.
 * Esta es la "puerta de entrada" de la API.
 */
@RestController // (1) Le dice a Spring que esto es un Controlador REST (devuelve JSON).
@RequestMapping("/api/productos") // (2) ¡MEJOR PRÁCTICA!
// Define la URL base para TODOS los métodos en esta clase.
// Todas las peticiones a /api/productos vendrán aquí.
public class ProductoController {

    // (3) Inyectamos el "cerebro" (el Servicio).
    // El Controlador *depende* del Servicio para hacer el trabajo.
    @Autowired
    private ProductoService productoService;

    // --- NUESTRO PRIMER ENDPOINT DE API ---

    /**
     * Endpoint para obtener todos los productos.
     * Responde a peticiones GET en /api/productos
     *
     * @return una lista de todos los productos en formato JSON.
     */
    @GetMapping // (4) Le dice a Spring: "Este método maneja peticiones HTTP GET".
    // Como ya tenemos @RequestMapping("/api/productos") en la clase,
    // este método se activa en la URL base (GET /api/productos).
    public List<Producto> obtenerTodosLosProductos() {
        // (5) El controlador NO hace lógica.
        // Simplemente llama al servicio y devuelve lo que este le da.
        return productoService.obtenerTodosLosProductos();
    }
}