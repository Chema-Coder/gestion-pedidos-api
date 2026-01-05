package com.dragoncoredev.gestion_pedidos_api.controller;

import com.dragoncoredev.gestion_pedidos_api.dto.DashboardDTO;
import com.dragoncoredev.gestion_pedidos_api.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // --- OBTENER RESUMEN (GET) ---
    @GetMapping
    public DashboardDTO obtenerResumen() {
        return dashboardService.obtenerMetricas();
    }
}