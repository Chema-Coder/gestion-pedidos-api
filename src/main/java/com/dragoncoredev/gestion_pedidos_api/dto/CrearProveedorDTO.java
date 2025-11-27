package com.dragoncoredev.gestion_pedidos_api.dto;

import java.math.BigDecimal;

/**
 * DTO para crear un nuevo Proveedor.
 * Define los datos necesarios para dar de alta a un proveedor.
 */
public record CrearProveedorDTO(
        String nombre,
        BigDecimal importeMinimoPedido
) {
}