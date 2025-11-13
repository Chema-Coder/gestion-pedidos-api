package com.dragoncoredev.gestion_pedidos_api.service;

// 1. Importamos la Entidad que vamos a manejar
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
// 2. Importamos el Repositorio que vamos a USAR
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;

// 3. Importamos las anotaciones de Spring
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired; // <-- ¡NUEVA ANOTACIÓN!

/**
 * Servicio para la lógica de negocio de Productos.
 * Este es el "cerebro" que decide QUÉ hacer.
 * Llama al Repositorio para que "haga" el acceso a datos.
 */
@Service // (1) Le dice a Spring que esta clase es un "Bean" de Servicio.
public class ProductoService {

    // --- ¡¡LECCIÓN DE MENTOR IMPORTANTE: INYECCIÓN DE DEPENDENCIAS!! ---
    //
    // (2) @Autowired: Esta es la "Inyección de Dependencias".
    // Le decimos a Spring: "Oye, cuando crees este ProductoService,
    // por favor, busca en tu 'contenedor de Beans' el objeto
    // ProductoRepository que ya creaste y 'enchúfamelo' aquí".
    //
    // ¡NUNCA escribimos 'new ProductoRepository()'! Dejamos que Spring lo gestione.
    //
    // (3) private final ...: Es una MEJOR PRÁCTICA (aunque hay debate)
    // inyectar a través del constructor. Por ahora, para mantenerlo simple,
    // usaremos la inyección de campos con @Autowired.
    // ¡Más adelante te enseñaré la inyección por constructor!

    @Autowired
    private ProductoRepository productoRepository;


    // --- NUESTRO PRIMER MÉTODO DE LÓGICA DE NEGOCIO ---

    /**
     * Obtiene una lista de TODOS los productos en la base de datos.
     * @return Una lista de entidades Producto.
     */
    public java.util.List<Producto> obtenerTodosLosProductos() {
        // (4) Simplemente llamamos al método 'findAll()' que JpaRepository
        // nos regaló en el ProductoRepository.
        return productoRepository.findAll();
    }
}