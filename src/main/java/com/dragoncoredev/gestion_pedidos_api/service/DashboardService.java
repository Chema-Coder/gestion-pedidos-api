package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.DashboardDTO;
import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.repository.PedidoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public DashboardService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public DashboardDTO obtenerMetricas() {
        // 1. Calcular Ingresos Totales (Sumar todos los pedidos)
        // Usamos programación funcional (Streams) para sumar
        List<Pedido> todosLosPedidos = pedidoRepository.findAll();
        double ingresos = todosLosPedidos.stream()
                .map(Pedido::getTotal) // Sacamos el total de cada pedido
                .reduce(BigDecimal.ZERO, BigDecimal::add) // Los sumamos
                .doubleValue(); // Convertimos a double simple

        // 2. Contar Pedidos Pendientes (Usando el método mágico del repo)
        long pendientes = pedidoRepository.countByEstado(EstadoPedido.EN_ESPERA_PROVEEDOR);

        // 3. Detectar Stock Bajo (Menos de 10 unidades)
        List<Producto> productosEscasos = productoRepository.findByStockLessThan(10);
        List<String> nombresAlerta = productosEscasos.stream()
                .map(p -> p.getNombre() + " (" + p.getStock() + ")") // Formato: "Tornillo (5)"
                .collect(Collectors.toList());

        // 4. Empaquetar todo en el DTO
        return new DashboardDTO(ingresos, pendientes, nombresAlerta);
    }
}