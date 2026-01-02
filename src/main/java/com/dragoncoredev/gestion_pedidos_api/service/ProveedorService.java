package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // --- 1. CREAR PROVEEDOR ---
    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // --- 2. LISTAR TODOS (GET) ---
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    // --- 3. BUSCAR POR ID (GET) ---
    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado ID: " + id));
    }

    // --- 4. ACTUALIZAR (PUT) ---
    public Proveedor actualizarProveedor(Long id, Proveedor datosNuevos) {
        Proveedor antiguo = obtenerPorId(id); // Reusamos el método de buscar

        antiguo.setNombre(datosNuevos.getNombre());
        antiguo.setImporteMinimoPedido(datosNuevos.getImporteMinimoPedido());

        return proveedorRepository.save(antiguo);
    }

    // --- 5. BORRAR (DELETE) ---
    public void borrarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado ID: " + id);
        }
        // OJO: Si intentas borrar un proveedor que tiene productos asignados,
        // la base de datos dará un error (DataIntegrityViolation) para proteger los datos.
        proveedorRepository.deleteById(id);
    }
}