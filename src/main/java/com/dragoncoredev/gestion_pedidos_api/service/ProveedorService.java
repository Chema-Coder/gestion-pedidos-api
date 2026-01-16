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

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado ID: " + id));
    }

    public Proveedor actualizarProveedor(Long id, Proveedor datosNuevos) {
        Proveedor antiguo = obtenerPorId(id);

        antiguo.setNombre(datosNuevos.getNombre());
        antiguo.setImporteMinimoPedido(datosNuevos.getImporteMinimoPedido());

        return proveedorRepository.save(antiguo);
    }

    public void borrarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }
}