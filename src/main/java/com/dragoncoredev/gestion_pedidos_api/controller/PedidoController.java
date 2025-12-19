package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * GET /api/pedidos
     * Devuelve todos los pedidos registrados.
     */
    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidoService.obtenerTodos();
    }

    /**
     * POST /api/pedidos
     * Crea un pedido nuevo (con sus líneas de detalle).
     * Recibe el JSON complejo con cabecera y lista de items.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // Añadimos @Valid para que se ejecuten las comprobaciones de los DTOs
    public Pedido crearPedido(@Valid @RequestBody CrearPedidoDTO dto) {
        return pedidoService.crearPedido(dto);
    }
}