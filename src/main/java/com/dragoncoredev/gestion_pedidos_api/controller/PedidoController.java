package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    // Inyección de dependencia por constructor
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // --- 1. CREAR PEDIDO (POST) ---
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crearPedido(@Valid @RequestBody CrearPedidoDTO dto) {
        return pedidoService.crearPedido(dto);
    }

    // --- 2. LISTAR TODOS (GET) ---
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    // --- 3. VER UN PEDIDO POR ID (GET) ---
    @GetMapping("/{id}")
    public Pedido verPedido(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }

    // --- 4. BORRAR PEDIDO (DELETE) ---
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve un 204 (Todo ok, sin cuerpo)
    public void eliminarPedido(@PathVariable Long id) {
        pedidoService.borrarPedido(id);
    }
}