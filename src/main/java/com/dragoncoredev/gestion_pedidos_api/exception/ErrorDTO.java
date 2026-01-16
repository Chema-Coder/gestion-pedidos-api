package com.dragoncoredev.gestion_pedidos_api.exception;

import java.time.LocalDateTime;

public record ErrorDTO(
        String mensaje,
        LocalDateTime fecha
) {
}