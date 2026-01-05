package com.dragoncoredev.gestion_pedidos_api.dto;

import java.util.List;

// Usamos 'record' porque es solo para transportar datos (inmutable)
public record DashboardDTO(
        Double ingresosTotales,         // Cuánto dinero suman todos los pedidos
        Long pedidosPendientes,         // Cuántos pedidos están esperando proveedor
        List<String> productosBajoStock // Lista de nombres de productos con poco stock
) {}