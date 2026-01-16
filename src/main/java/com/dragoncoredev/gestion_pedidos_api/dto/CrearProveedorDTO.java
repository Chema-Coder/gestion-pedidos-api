package com.dragoncoredev.gestion_pedidos_api.dto;

import java.math.BigDecimal;

public record CrearProveedorDTO(
        String nombre,
        BigDecimal importeMinimoPedido
) {
}