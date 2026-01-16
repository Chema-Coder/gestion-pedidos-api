package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Catálogo de Productos", description = "Operaciones para gestionar el inventario, precios y stock")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto", description = "Registra un producto con stock inicial y asignado a un proveedor existente.")
    public Producto crearProducto(@Valid @RequestBody CrearProductoDTO dto) {
        return productoService.crearProducto(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Devuelve la lista completa de productos disponibles en base de datos.")
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Obtiene el detalle de un producto específico. Lanza error 404 si no existe.")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica datos básicos y stock. El precio se actualiza en tiempo real.")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }
}