package com.dragoncoredev.gestion_pedidos_api.repository;

// 1. La entidad que este repositorio va a gestionar
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
// 2. La "magia" de Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
// 3. Le decimos a Spring que esto es un "Bean" de Repositorio
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Producto.
 * * Al extender JpaRepository, Spring Data JPA nos provee mágicamente
 * todos los métodos CRUD (Create, Read, Update, Delete) básicos.
 */
@Repository // (Opcional pero buena práctica) Anota esto como un Repositorio Spring
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // --- ¡YA ESTÁ! ---
    //
    // En este momento, SIN ESCRIBIR NADA MÁS,
    // ya tenemos métodos disponibles como:
    //
    // -> Producto save(Producto producto);            (Para crear y actualizar)
    // -> Optional<Producto> findById(Long id);      (Para buscar uno por su ID)
    // -> List<Producto> findAll();                  (Para buscar todos)
    // -> void deleteById(Long id);                   (Para borrar)
    // -> long count();                              (Para contar)
    //
    // Y muchos más...

    // Más adelante, aquí podremos definir métodos de búsqueda personalizados
    // E.g: List<Producto> findByNombreContaining(String nombre);
    // Y Spring lo implementará solo, ¡solo por el nombre!

    // Busca productos que tengan MENOS stock que la cantidad indicada
    java.util.List<Producto> findByStockLessThan(Integer cantidad);
}