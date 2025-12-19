package com.dragoncoredev.gestion_pedidos_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoItemDTO(
        @NotNull(message = "El ID del producto es obligatorio")
        Long productoId,

        @Positive(message = "La cantidad debe ser mayor que cero")
        Integer cantidad
) {
}