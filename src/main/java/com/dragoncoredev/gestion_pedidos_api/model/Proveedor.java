package com.dragoncoredev.gestionpedidosapi.model;

import com.dragoncoredev.gestionpedidosapi.model.Producto;
// Importamos todas las anotaciones de JPA.
// ¡OJO! En Spring Boot 3+ siempre usamos 'jakarta.persistence', nunca 'javax.persistence'
import jakarta.persistence.*;

// Importamos las herramientas de Lombok
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/*
 * --- ANOTACIONES DE LOMBOK ---
 * @Getter: Genera todos los métodos getter (ej: getId(), getNombre()).
 * @Setter: Genera todos los métodos setter (ej: setId(Long id), setNombre(String nombre)).
 * @NoArgsConstructor: Genera un constructor vacío (public Proveedor() {}).
 * ¡JPA necesita este constructor vacío para poder crear las entidades!
 * @AllArgsConstructor: Genera un constructor con todos los campos (útil para tests).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/*
 * --- ANOTACIONES DE JPA (Jakarta Persistence API) ---
 */
// (1) Le dice a JPA que esta clase es una entidad que debe ser mapeada a la BBDD.
@Entity
// (2) Opcional: especifica el nombre exacto de la tabla.
// Por defecto, JPA usaría el nombre de la clase ("Proveedor").
// Es una convención muy común usar plurales y 'snake_case' para los nombres de tablas.
@Table(name = "proveedores")
public class Proveedor {

    // (3) Marca este campo como la Clave Primaria (Primary Key) de la tabla.
    @Id
    // (4) Configura cómo se genera el valor de la PK.
    // 'GenerationType.IDENTITY' le delega la tarea a la BBDD,
    // que usará su columna auto-incrementable (ej: 'bigserial' en PostgreSQL).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // (5) Opcional: personaliza la columna.
    // 'nullable = false' -> Añade una restricción 'NOT NULL' a la columna.
    // 'unique = true' -> Añade una restricción 'UNIQUE'. No puede haber dos proveedores con el mismo nombre.
    @Column(nullable = false, unique = true)
    private String nombre;

    // Esta es la regla de negocio clave que mencionaste.
    // Por defecto, se mapeará a un tipo 'numeric' en PostgreSQL, lo cual es perfecto para dinero.
    @Column(nullable = false)
    private BigDecimal importeMinimoPedido = BigDecimal.ZERO; // Buena práctica: inicializarlo.

    /*
     * --- DEFINICIÓN DE LA RELACIÓN ---
     * Un Proveedor puede suministrar muchos Productos.
     * Es una relación Uno-a-Muchos (One-to-Many).
     *
     * "mappedBy = 'proveedor'": Esta es la parte más importante.
     * Le dice a JPA: "Yo (Proveedor) no soy el dueño de esta relación.
     * El dueño es el campo llamado 'proveedor' en la clase Producto".
     * Esto evita que se cree una tabla intermedia innecesaria.
     *
     * "cascade = CascadeType.ALL": Le dice a JPA: "Si un Proveedor es eliminado,
     * elimina automáticamente todos sus Productos asociados".
     *
     * "orphanRemoval = true": Si quito un producto de esta lista (pero no lo borro),
     * JPA entiende que ese producto se ha quedado "huérfano" y debe ser borrado de la BBDD.
     */
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

}