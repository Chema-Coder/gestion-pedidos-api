package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProveedorDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // (1) ¡Etiqueta de Cerebro!
public class ProveedorService {

    @Autowired // (2) Inyectamos la caja de herramientas
    private ProveedorRepository proveedorRepository;

    /**
     * Obtiene todos los proveedores.
     */
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    /**
     * Crea un nuevo proveedor a partir de los datos del DTO.
     */
    public Proveedor crearProveedor(CrearProveedorDTO dto) {
        // 1. Creamos la entidad vacía
        Proveedor nuevoProveedor = new Proveedor();

        // 2. Mapeamos los datos del DTO a la Entidad
        nuevoProveedor.setNombre(dto.nombre());
        nuevoProveedor.setImporteMinimoPedido(dto.importeMinimoPedido());

        // 3. Guardamos en base de datos
        return proveedorRepository.save(nuevoProveedor);
    }
}