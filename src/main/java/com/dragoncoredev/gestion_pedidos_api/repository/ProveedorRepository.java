package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}