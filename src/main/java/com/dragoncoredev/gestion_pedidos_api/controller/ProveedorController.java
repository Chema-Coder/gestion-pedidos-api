package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.service.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // 1. Crear
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.crearProveedor(proveedor);
    }

    // 2. Listar Todos
    @GetMapping
    public List<Proveedor> listarProveedores() {
        return proveedorService.listarTodos();
    }

    // 3. Ver uno
    @GetMapping("/{id}")
    public Proveedor verProveedor(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id);
    }

    // 4. Editar
    @PutMapping("/{id}")
    public Proveedor actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizarProveedor(id, proveedor);
    }

    // 5. Borrar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProveedor(@PathVariable Long id) {
        proveedorService.borrarProveedor(id);
    }
}