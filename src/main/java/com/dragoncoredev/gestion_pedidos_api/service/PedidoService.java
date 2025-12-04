package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.dto.PedidoItemDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.PedidoItem;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.model.Proveedor; // Necesario para consultar el mínimo
import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido; // Corregido: ya no está en .enums
import com.dragoncoredev.gestion_pedidos_api.repository.PedidoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene todos los pedidos.
     */
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Crea un pedido completo, calcula totales y aplica reglas de negocio.
     *
     * @Transactional: Si algo falla (ej: producto no existe), se hace rollback
     * de todo el proceso para no dejar datos corruptos.
     */
    @Transactional
    public Pedido crearPedido(CrearPedidoDTO dto) {
        // 1. Crear la Cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.nombreCliente());
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEsPedidoInterno(false);

        // Inicializamos el total acumulado en CERO
        BigDecimal totalAcumulado = BigDecimal.ZERO;

        // Variable para recordar al proveedor y validar su regla de mínimo.
        // (En este MVP asumimos que el pedido es monoveedor o validamos contra el primero que encontremos).
        Proveedor proveedorDelPedido = null;

        // 2. Procesar las líneas (Items)
        for (PedidoItemDTO itemDto : dto.items()) {
            // A. Buscar el producto real
            Producto producto = productoRepository.findById(itemDto.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + itemDto.productoId()));

            // Capturamos el proveedor del primer producto para validar las reglas después
            if (proveedorDelPedido == null) {
                proveedorDelPedido = producto.getProveedor();
            }

            // B. Crear la entidad PedidoItem
            PedidoItem item = new PedidoItem();
            item.setCantidad(itemDto.cantidad());
            item.setProducto(producto);

            // C. Conectar la relación bidireccional (Padre <-> Hijo)
            item.setPedido(pedido);
            pedido.getItems().add(item);

            // D. --- CÁLCULO DEL PRECIO DE LA LÍNEA ---
            // Precio = PrecioProducto * Cantidad
            // En Java (BigDecimal) se usa .multiply()
            BigDecimal precioLinea = producto.getPrecio().multiply(new BigDecimal(itemDto.cantidad()));

            // Sumamos al total general del pedido
            totalAcumulado = totalAcumulado.add(precioLinea);
        }

        // 3. Guardar el Total calculado en el pedido
        pedido.setTotal(totalAcumulado);

        // 4. --- REGLA DE NEGOCIO: VALIDAR IMPORTE MÍNIMO ---
        // Decidimos el estado inicial basándonos en el dinero.
        if (proveedorDelPedido != null) {
            BigDecimal importeMinimo = proveedorDelPedido.getImporteMinimoPedido();

            // Comparamos: ¿Es totalAcumulado >= importeMinimo?
            // (compareTo devuelve: 1 si es mayor, 0 si es igual, -1 si es menor)
            if (totalAcumulado.compareTo(importeMinimo) >= 0) {
                // ¡Éxito! Supera el mínimo, el pedido puede salir.
                pedido.setEstado(EstadoPedido.PEDIDO_A_PROVEEDOR);
            } else {
                // Fracaso. No llega al mínimo. Se queda retenido.
                pedido.setEstado(EstadoPedido.EN_ESPERA_PROVEEDOR);
            }
        } else {
            // Caso borde: Si no hubiera proveedor, estado por defecto.
            pedido.setEstado(EstadoPedido.RECIBIDO_CLIENTE);
        }

        // 5. Guardar todo en la BBDD (Pedido + Items)
        return pedidoRepository.save(pedido);
    }
}