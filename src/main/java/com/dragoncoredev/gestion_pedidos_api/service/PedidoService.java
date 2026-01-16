package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.dto.PedidoItemDTO;
import com.dragoncoredev.gestion_pedidos_api.model.*;
import com.dragoncoredev.gestion_pedidos_api.repository.PedidoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    // --- 1. CREAR PEDIDO (CON CONTROL DE STOCK) ---
    @Transactional
    public Pedido crearPedido(CrearPedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.nombreCliente());
        pedido.setFechaCreacion(java.time.LocalDateTime.now());
        pedido.setEsPedidoInterno(false);

        BigDecimal totalAcumulado = BigDecimal.ZERO;

        for (PedidoItemDTO itemDTO : dto.items()) {

            Producto producto = productoRepository.findById(itemDTO.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + itemDTO.productoId()));

            // --- LÓGICA DE STOCK ---

            if (producto.getStock() < itemDTO.cantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre()
                        + ". Disponible: " + producto.getStock()
                        + ", Solicitado: " + itemDTO.cantidad());
            }

            producto.setStock(producto.getStock() - itemDTO.cantidad());
            productoRepository.save(producto);


            PedidoItem item = new PedidoItem();
            item.setCantidad(itemDTO.cantidad());
            item.setProducto(producto);
            item.setPedido(pedido);
            item.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal = BigDecimal.valueOf(producto.getPrecio())
                    .multiply(BigDecimal.valueOf(itemDTO.cantidad()));
            totalAcumulado = totalAcumulado.add(subtotal);

            pedido.getItems().add(item);
        }

        pedido.setTotal(totalAcumulado);

        // Lógica de Estado
        if (totalAcumulado.compareTo(new BigDecimal("50.00")) > 0) {
            pedido.setEstado(EstadoPedido.PEDIDO_A_PROVEEDOR);
        } else {
            pedido.setEstado(EstadoPedido.EN_ESPERA_PROVEEDOR);
        }

        return pedidoRepository.save(pedido);
    }

    // --- 2. LISTAR TODOS ---
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // --- 3. BUSCAR POR ID ---
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado ID: " + id));
    }

    // --- 4. BORRAR PEDIDO ---
    public void borrarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido no encontrado ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    // --- 5. BUSCAR POR ESTADO ---
    public List<Pedido> buscarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }
}