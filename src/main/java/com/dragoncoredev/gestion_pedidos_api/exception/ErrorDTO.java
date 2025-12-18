package com.dragoncoredev.gestion_pedidos_api.exception;

import java.time.LocalDateTime;

/**
 * Molde para nuestros mensajes de error bonitos.
 */
public record ErrorDTO(
        String mensaje,
        LocalDateTime fecha
) {
}