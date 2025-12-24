package com.dragoncoredev.gestion_pedidos_api.service;

// 1. Importamos las Entidades que vamos a manejar
import com.dragoncoredev.gestion_pedidos_api.model.Producto;
import com.dragoncoredev.gestion_pedidos_api.model.Proveedor; // <-- ¡NUEVO IMPORT!

// 2. Importamos el DTO que vamos a recibir
import com.dragoncoredev.gestion_pedidos_api.dto.CrearProductoDTO; // <-- ¡NUEVO IMPORT!

// 3. Importamos los Repositorios que vamos a USAR
import com.dragoncoredev.gestion_pedidos_api.repository.ProductoRepository;
import com.dragoncoredev.gestion_pedidos_api.repository.ProveedorRepository; // <-- ¡NUEVO IMPORT!

// 4. Importamos las anotaciones de Spring
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

// 5. Importamos la excepción para "No Encontrado" (¡luego la crearemos!)
import jakarta.persistence.EntityNotFoundException; // <-- ¡NUEVO IMPORT!

import java.util.List; // Necesitamos esto para el método antiguo

/**
 * Servicio para la lógica de negocio de Productos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // --- ¡NUEVA INYECCIÓN! ---
    // Ahora el Servicio también necesita la "caja de herramientas" de Proveedor
    // para poder buscar un proveedor por su ID.
    @Autowired
    private ProveedorRepository proveedorRepository;


    /**
     * Obtiene una lista de TODOS los productos en la base de datos.
     * @return Una lista de entidades Producto.
     */
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }


    // --- ¡NUEVO MÉTODO CON LÓGICA DE NEGOCIO! ---

    /**
     * Crea un nuevo producto en la base de datos a partir de un DTO.
     * @param productoDTO El DTO con la información para crear el producto.
     * @return El Producto entidad que ha sido guardado (ya con su ID).
     */
    public Producto crearProducto(CrearProductoDTO productoDTO) {

        // --- LÓGICA DE NEGOCIO (Validación) ---
        // 1. Buscamos al Proveedor usando el ID del DTO.
        //    'findById' devuelve un 'Optional'. Usamos '.orElseThrow()'
        //    para obtener el Proveedor o lanzar un error si no existe.
        Proveedor proveedor = proveedorRepository.findById(productoDTO.proveedorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el Proveedor con ID: " + productoDTO.proveedorId()
                ));

        // --- LÓGICA DE NEGOCIO (Mapeo) ---
        // 2. Creamos una nueva entidad Producto (¡vacía!).
        Producto nuevoProducto = new Producto();

        // 3. Mapeamos manualmente los datos del DTO a la Entidad.
        //    (¡Nunca le pasamos el DTO directamente a la BBDD!)
        nuevoProducto.setNombre(productoDTO.nombre());
        nuevoProducto.setDescripcion(productoDTO.descripcion());
        nuevoProducto.setPrecio(productoDTO.precio());

        // 4. Asignamos la entidad Proveedor completa que encontramos.
        nuevoProducto.setProveedor(proveedor);

        // --- ACCIÓN DE GUARDADO ---
        // 5. Usamos el repositorio para guardar la nueva entidad en la BBDD.
        //    El método 'save()' devuelve la entidad guardada (¡con el ID asignado!).
        return productoRepository.save(nuevoProducto);
    }

    // --- ACTUALIZAR PRODUCTO (PUT) ---
    public Producto actualizarProducto(Long id, Producto datosNuevos) {
        // 1. Buscamos el producto antiguo (o fallamos si no existe)
        Producto productoAntiguo = productoRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Producto no encontrado ID: " + id));

        // 2. Actualizamos los campos (Nombre, Descripción, Precio)
        productoAntiguo.setNombre(datosNuevos.getNombre());
        productoAntiguo.setDescripcion(datosNuevos.getDescripcion());
        productoAntiguo.setPrecio(datosNuevos.getPrecio());

        // 3. Guardamos los cambios
        return productoRepository.save(productoAntiguo);
    }

    // --- MÉTODOS DE LECTURA (GET) ---

    public java.util.List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Producto no encontrado ID: " + id));
    }
}