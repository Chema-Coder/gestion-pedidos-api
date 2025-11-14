package com.dragoncoredev.gestion_pedidos_api.dto;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para crear un nuevo Producto.
 * Esto define el "contrato" de la API, lo que esperamos recibir.
 * Usamos un 'record' para una definición concisa e inmutable.
 *
 * @param nombre El nombre del producto.
 * @param descripcion Una descripción opcional.
 * @param precio El precio del producto.
 * @param proveedorId El ID del Proveedor al que este producto pertenece.
 */
public record CrearProductoDTO(
        String nombre,
        String descripcion,
        BigDecimal precio,
        Long proveedorId // ¡Nota clave! Pedimos el ID, no la entidad Proveedor entera.
) {
}

