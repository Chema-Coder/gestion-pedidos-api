package com.dragoncoredev.gestion_pedidos_api.dto;

import java.util.List;

public record DashboardDTO(
        Double ingresosTotales,
        Long pedidosPendientes,
        List<String> productosBajoStock
) {}