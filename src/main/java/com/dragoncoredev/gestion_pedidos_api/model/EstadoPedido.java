package com.dragoncoredev.gestionpedidosapi.model;

/**
 * Representa los estados por los que puede pasar un pedido
 * a lo largo de su ciclo de vida.
 */
public enum EstadoPedido {
    RECIBIDO_CLIENTE,       // El cliente lo ha pedido
    EN_ESPERA_PROVEEDOR,    // En cola, esperando alcanzar el mínimo
    PEDIDO_A_PROVEEDOR,     // Pedido realizado al proveedor
    RECIBIDO_EN_TIENDA,     // El paquete ha llegado
    ENTREGADO_CLIENTE       // El cliente lo ha recogido
}