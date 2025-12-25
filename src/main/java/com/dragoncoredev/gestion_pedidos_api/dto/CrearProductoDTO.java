package com.dragoncoredev.gestion_pedidos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

public record CrearProductoDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor que cero")
        Double precio,

        // --- NUEVO CAMPO ---
        // No le ponemos @NotNull para que sea opcional (si no lo mandan, será 0)
        @Positive(message = "El stock no puede ser negativo")
        Integer stock
) {}