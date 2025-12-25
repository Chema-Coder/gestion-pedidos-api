package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- 1. CREAR PRODUCTO (POST) ---
    // Ahora lee el stock del DTO
    public Producto crearProducto(CrearProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());

        // Asignamos el stock (si viene nulo, ponemos 0)
        producto.setStock(dto.stock() != null ? dto.stock() : 0);

        return productoRepository.save(producto);
    }

    // --- 2. LISTAR TODOS (GET) ---
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // --- 3. BUSCAR POR ID (GET) ---
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + id));
    }

    // --- 4. ACTUALIZAR PRODUCTO (PUT) ---
    // Ahora permite modificar el stock
    public Producto actualizarProducto(Long id, Producto datosNuevos) {
        Producto productoAntiguo = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + id));

        // Actualizamos datos básicos
        productoAntiguo.setNombre(datosNuevos.getNombre());
        productoAntiguo.setDescripcion(datosNuevos.getDescripcion());
        productoAntiguo.setPrecio(datosNuevos.getPrecio());

        // Actualizamos stock SOLO si nos envían un dato (para no borrarlo accidentalmente)
        if (datosNuevos.getStock() != null) {
            productoAntiguo.setStock(datosNuevos.getStock());
        }

        return productoRepository.save(productoAntiguo);
    }
}