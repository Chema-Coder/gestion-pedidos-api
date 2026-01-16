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

        @Positive(message = "El stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "El ID del proveedor es obligatorio")
        Long proveedorId
) {}