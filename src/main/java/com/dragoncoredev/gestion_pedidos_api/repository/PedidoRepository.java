package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido;
import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);
    long countByEstado(com.dragoncoredev.gestion_pedidos_api.model.EstadoPedido estado);
}