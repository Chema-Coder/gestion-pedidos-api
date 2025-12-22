package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Pedido.
 * Hereda todos los métodos CRUD de JpaRepository.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // ¡Listo! Ya tenemos save(), findById(), findAll(), etc.
    // para la entidad Pedido.

    // ¡Magia! Solo con definir esto, Spring sabe hacer el "WHERE estado = ?"
    List<Pedido> findByEstado(EstadoPedido estado);
}