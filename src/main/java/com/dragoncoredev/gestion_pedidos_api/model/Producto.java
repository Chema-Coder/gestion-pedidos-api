package com.dragoncoredev.gestion_pedidos_api.model;

import com.dragoncoredev.gestion_pedidos_api.model.Proveedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    /*
     * --- DEFINICIÓN DE LA RELACIÓN INVERSA ---
     * Esta es la otra mitad de la relación.
     * Muchos Productos (@Many) pueden pertenecer a UN Proveedor (@ToOne).
     *
     * (1) @ManyToOne: Define la relación.
     *
     * (2) fetch = FetchType.LAZY: ¡¡MEJOR PRÁCTICA DE RENDIMIENTO!!
     * Le dice a JPA: "Cuando cargue un Producto de la BBDD,
     * NO cargues a su Proveedor asociado inmediatamente.
     * Solo cárgalo si alguien llama al método getProveedor()".
     * Esto evita cargar datos innecesarios de la BBDD.
     *
     * (3) @JoinColumn(name = "proveedor_id", nullable = false):
     * ¡Esta es la anotación de "dueño"! Le dice a JPA:
     * "En la tabla 'productos', crea una columna llamada 'proveedor_id'
     * para guardar la Clave Foránea (Foreign Key) que apunta al 'id' del Proveedor.
     * Esta columna no puede ser nula".
     *
     * Esto hace que 'Producto' sea el "dueño" de la relación.
     */
    // ... (otras anotaciones) ...

    @JsonIgnore // <-- ¡LA SOLUCIÓN! Le dice a Jackson que ignore este campo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
}