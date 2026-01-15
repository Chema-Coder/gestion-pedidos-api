package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.DashboardDTO;
import com.dragoncoredev.gestion_pedidos_api.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Cuadro de Mando", description = "Métricas clave y alertas en tiempo real para la toma de decisiones.")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Obtener resumen ejecutivo", description = "Calcula ingresos totales, pedidos pendientes y detecta productos con stock crítico.")
    public DashboardDTO obtenerResumen() {
        return dashboardService.obtenerMetricas();
    }
}