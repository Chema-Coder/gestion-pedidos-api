package com.dragoncoredev.gestion_pedidos_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CrearPedidoDTO(
        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        String nombreCliente,

        @NotEmpty(message = "El pedido debe tener al menos una línea")
        @Valid // <-- ¡OJO! Esto le dice a Spring que entre a validar también los objetos de la lista
        List<PedidoItemDTO> items
) {
}