package com.dragoncoredev.gestion_pedidos_api.dto;

import java.util.List;

/**
 * DTO para crear un pedido completo.
 * Contiene la información de cabecera y la lista de productos.
 *
 * @param nombreCliente El nombre del cliente que hace el pedido.
 * @param items La lista de productos y cantidades que quiere pedir.
 */
public record CrearPedidoDTO(
        String nombreCliente,
        List<PedidoItemDTO> items // ¡Aquí usamos el DTO que acabamos de crear!
) {
}