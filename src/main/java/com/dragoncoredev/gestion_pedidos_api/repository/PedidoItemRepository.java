package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad PedidoItem.
 * Hereda todos los métodos CRUD de JpaRepository.
 */
@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    // ¡Listo! Ya tenemos save(), findById(), findAll(), etc.
    // para la entidad PedidoItem.
}