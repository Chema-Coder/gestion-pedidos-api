package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearProveedorDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores") // (1) URL Base: Todas las peticiones aquí serán para proveedores
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService; // (2) Inyectamos el cerebro

    /**
     * GET /api/proveedores
     * Devuelve la lista completa de proveedores.
     */
    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodos();
    }

    /**
     * POST /api/proveedores
     * Crea un nuevo proveedor.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // (3) Devolvemos 201 Created
    public Proveedor crearProveedor(@RequestBody CrearProveedorDTO dto) {
        return proveedorService.crearProveedor(dto);
    }
}