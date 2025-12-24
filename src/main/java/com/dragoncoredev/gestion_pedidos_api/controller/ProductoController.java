package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO; // <--- IMPORTANTE: Añadido
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // --- 1. CREAR (POST) ---
    // CORREGIDO: Ahora recibe CrearProductoDTO en lugar de Producto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crearProducto(@RequestBody CrearProductoDTO dto) {
        return productoService.crearProducto(dto);
    }

    // --- 2. LISTAR TODOS (GET) ---
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // --- 3. VER UNO POR ID (GET) ---
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    // --- 4. EDITAR (PUT) ---
    @PutMapping("/{id}")
    public Producto editarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }
}