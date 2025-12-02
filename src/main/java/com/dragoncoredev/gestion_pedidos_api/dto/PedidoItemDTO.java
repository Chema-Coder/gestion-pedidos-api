package com.dragoncoredev.gestion_pedidos_api.dto;

/**
 * DTO para representar una línea de pedido individual.
 * Se usará dentro de la lista de items al crear un pedido.
 *
 * @param productoId El ID del producto que se quiere pedir.
 * @param cantidad La cantidad de unidades.
 */
public record PedidoItemDTO(
        Long productoId,
        Integer cantidad
) {
}