package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.dto.PedidoItemDTO;
import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.repository.PedidoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void crearPedido_DeberiaCrearPedidoYRestarStock_CuandoTodoEsCorrecto() {
        Producto productoSimulado = new Producto();
        productoSimulado.setId(1L);
        productoSimulado.setNombre("Silla Gamer");
        productoSimulado.setPrecio(100.00);
        productoSimulado.setStock(5);

        PedidoItemDTO itemDTO = new PedidoItemDTO(1L, 2);
        CrearPedidoDTO pedidoDTO = new CrearPedidoDTO("Josemari", List.of(itemDTO));

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoSimulado));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(i -> i.getArguments()[0]);

        Pedido resultado = pedidoService.crearPedido(pedidoDTO);

        assertNotNull(resultado);
        assertEquals("Josemari", resultado.getNombreCliente());
        assertEquals(3, productoSimulado.getStock());
        assertEquals(0, new BigDecimal("200.00").compareTo(resultado.getTotal()));
        assertEquals(EstadoPedido.PEDIDO_A_PROVEEDOR, resultado.getEstado());

        verify(productoRepository, times(1)).save(productoSimulado);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void crearPedido_DeberiaLanzarExcepcion_CuandoNoHayStock() {
        Producto productoSimulado = new Producto();
        productoSimulado.setId(1L);
        productoSimulado.setNombre("Silla Gamer");
        productoSimulado.setStock(2);

        PedidoItemDTO itemDTO = new PedidoItemDTO(1L, 5);
        CrearPedidoDTO pedidoDTO = new CrearPedidoDTO("Josemari", List.of(itemDTO));

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoSimulado));

        assertThrows(IllegalArgumentException.class, () -> pedidoService.crearPedido(pedidoDTO));

        verify(productoRepository, never()).save(any(Producto.class));
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void crearPedido_DeberiaLanzarExcepcion_CuandoProductoNoExiste() {
        PedidoItemDTO itemDTO = new PedidoItemDTO(99L, 1);
        CrearPedidoDTO pedidoDTO = new CrearPedidoDTO("Josemari", List.of(itemDTO));

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoService.crearPedido(pedidoDTO));

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }
}