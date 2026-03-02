package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerPorId_DeberiaRetornarProducto_CuandoExiste() {
        Long idProducto = 1L;
        Producto productoSimulado = new Producto();
        productoSimulado.setId(idProducto);
        productoSimulado.setNombre("Teclado Mecánico");
        productoSimulado.setPrecio(50.00);
        productoSimulado.setStock(10);

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(productoSimulado));

        Producto resultado = productoService.obtenerPorId(idProducto);

        assertNotNull(resultado);
        assertEquals("Teclado Mecánico", resultado.getNombre());
        assertEquals(10, resultado.getStock());

        verify(productoRepository, times(1)).findById(idProducto);
    }

    @Test
    void obtenerPorId_DeberiaLanzarExcepcion_CuandoNoExiste() {
        Long idProducto = 999L;

        when(productoRepository.findById(idProducto)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.obtenerPorId(idProducto));

        verify(productoRepository, times(1)).findById(idProducto);
    }
}