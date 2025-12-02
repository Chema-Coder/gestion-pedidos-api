package com.dragoncoredev.gestion_pedidos_api.service;

import com.dragoncoredev.gestion_pedidos_api.dto.CrearPedidoDTO;
import com.dragoncoredev.gestion_pedidos_api.dto.PedidoItemDTO;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.PedidoItem;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.repository.PedidoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional; // ¡IMPORTANTE! Usar jakarta.transaction o org.springframework.transaction
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository; // Necesitamos buscar productos

    /**
     * Obtiene todos los pedidos.
     */
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Crea un pedido completo con sus líneas de detalle.
     *
     * @Transactional: ¡VITAL!
     * Significa "Todo o Nada". Si guardamos la cabecera del pedido,
     * pero falla el guardado de una línea (ej: producto no existe),
     * Spring hará un "Rollback" y borrará la cabecera.
     * Evita tener pedidos "zombis" sin líneas o datos corruptos.
     */
    @Transactional
    public Pedido crearPedido(CrearPedidoDTO dto) {
        // 1. Crear la Cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.nombreCliente());
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.RECIBIDO_CLIENTE); // Estado inicial por defecto
        pedido.setEsPedidoInterno(false);

        // 2. Procesar las líneas (Items)
        // Recorremos la lista de items que nos envía el DTO
        for (PedidoItemDTO itemDto : dto.items()) {
            // A. Buscamos el producto real (o fallamos si no existe)
            Producto producto = productoRepository.findById(itemDto.productoId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado ID: " + itemDto.productoId()));

            // B. Creamos la entidad PedidoItem
            PedidoItem item = new PedidoItem();
            item.setCantidad(itemDto.cantidad());
            item.setProducto(producto);

            // C. ¡LA CLAVE DE LA RELACIÓN BIDIRECCIONAL!
            // Conectamos el hijo con el padre
            item.setPedido(pedido);

            // Conectamos el padre con el hijo (añadiendo a la lista)
            pedido.getItems().add(item);
        }

        // 3. Guardar en Cascada
        // Gracias a 'cascade = CascadeType.ALL' en la entidad Pedido,
        // al guardar el pedido, JPA guardará automáticamente todos los items.
        return pedidoRepository.save(pedido);
    }
}