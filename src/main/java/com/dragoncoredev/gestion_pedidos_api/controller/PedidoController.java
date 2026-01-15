package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Gestión de Pedidos", description = "Procesamiento de ventas, control de estados y facturación.")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo pedido", description = "Registra una venta, descuenta stock automáticamente y calcula el total.")
    public Pedido crearPedido(@Valid @RequestBody CrearPedidoDTO orden) {
        return pedidoService.crearPedido(orden);
    }

    @GetMapping
    @Operation(summary = "Historial completo", description = "Lista todos los pedidos realizados hasta la fecha.")
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver detalle del pedido", description = "Muestra la información completa de un pedido, incluyendo sus líneas de detalle.")
    public Pedido verPedido(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar pedido", description = "Borra un pedido del sistema.")
    public void eliminarPedido(@PathVariable Long id) {
        pedidoService.borrarPedido(id);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Filtrar por estado", description = "Busca pedidos según su situación (ej: PENDIENTE, ENVIADO...).")
    public List<Pedido> buscarPorEstado(@RequestParam EstadoPedido estado) {
        return pedidoService.buscarPorEstado(estado);
    }
}