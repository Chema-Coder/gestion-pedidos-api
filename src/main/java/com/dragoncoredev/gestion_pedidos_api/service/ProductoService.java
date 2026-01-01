package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProveedorRepository; // Importante
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository; // Nuevo repositorio inyectado

    // Constructor actualizado con las dos dependencias
    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    // --- 1. CREAR PRODUCTO (POST) ---
    public Producto crearProducto(CrearProductoDTO dto) {
        // Buscamos el proveedor primero
        Proveedor proveedor = proveedorRepository.findById(dto.proveedorId())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado ID: " + dto.proveedorId()));

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());
        // Asignamos el stock (si viene nulo, ponemos 0)
        producto.setStock(dto.stock() != null ? dto.stock() : 0);

        // ¡Asignamos el proveedor!
        producto.setProveedor(proveedor);

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
    public Producto actualizarProducto(Long id, Producto datosNuevos) {
        Producto productoAntiguo = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + id));

        productoAntiguo.setNombre(datosNuevos.getNombre());
        productoAntiguo.setDescripcion(datosNuevos.getDescripcion());
        productoAntiguo.setPrecio(datosNuevos.getPrecio());

        if (datosNuevos.getStock() != null) {
            productoAntiguo.setStock(datosNuevos.getStock());
        }

        // Nota: De momento no implementamos cambiar de proveedor en el PUT para no complicarlo

        return productoRepository.save(productoAntiguo);
    }
}