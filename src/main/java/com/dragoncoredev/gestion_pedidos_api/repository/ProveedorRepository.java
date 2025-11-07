package com.dragoncoredev.gestion_pedidos_api.repository;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Proveedor.
 * Hereda todos los métodos CRUD de JpaRepository.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // ¡Listo! Ya tenemos save(), findById(), findAll(), etc.
    // para la entidad Proveedor.
}