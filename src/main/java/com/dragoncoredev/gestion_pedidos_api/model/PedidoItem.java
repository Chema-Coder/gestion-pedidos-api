package com.dragoncoredev.gestion_pedidos_api.model;

import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_items")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELACIÓN CON PEDIDO (El "Padre") ---
    // MUCHOS PedidoItems (Many) pertenecen a UN Pedido (One).
    //
    // "fetch = FetchType.LAZY":
    //     ¡Mejor práctica de rendimiento! No cargues el Pedido
    //     completo solo por cargar un PedidoItem.
    //
    // "@JoinColumn(name = 'pedido_id')":
    //     ¡Esta es la "dueña" de la relación!
    //     Esta anotación crea la columna de clave foránea 'pedido_id'
    //     en esta tabla ('pedido_items').
    //     Esto completa la relación que definimos en Pedido.java
    //     con el "mappedBy = 'pedido'".
    @JsonIgnore // <-- ¡ESTA ES LA CLAVE! Rompe el bucle infinito.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    // --- RELACIÓN CON PRODUCTO (El "Artículo") ---
    // ¡AQUÍ ES DONDE NECESITAMOS EL CAMBIO!
    // Buscamos el campo 'producto' y ponemos FetchType.EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;


    // --- CAMPO DE DATOS PROPIO ---
    // Esta es la información que esta entidad aporta a la relación.
    @Column(nullable = false)
    private Integer cantidad;

}