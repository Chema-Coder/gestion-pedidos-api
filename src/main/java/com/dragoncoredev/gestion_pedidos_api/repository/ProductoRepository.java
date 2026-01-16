package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByStockLessThan(Integer cantidad);
}