package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import com.dragoncoredev.gestion_pedidos_api.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Gestión de Proveedores", description = "Administración de empresas proveedoras y sus condiciones de venta.")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nuevo proveedor", description = "Crea una nueva ficha de proveedor con su importe mínimo de pedido.")
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.crearProveedor(proveedor);
    }

    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Obtiene el listado completo de proveedores activos.")
    public List<Proveedor> listarProveedores() {
        return proveedorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proveedor por ID", description = "Recupera los datos de un proveedor específico.")
    public Proveedor verProveedor(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar proveedor", description = "Actualiza el nombre o las condiciones del proveedor.")
    public Proveedor actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizarProveedor(id, proveedor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar proveedor", description = "Borra un proveedor del sistema. Cuidado: puede fallar si tiene productos asociados.")
    public void eliminarProveedor(@PathVariable Long id) {
        proveedorService.borrarProveedor(id);
    }
}