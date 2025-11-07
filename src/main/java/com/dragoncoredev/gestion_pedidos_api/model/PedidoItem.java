package com.dragoncoredev.gestion_pedidos_api.model;

import com.dragoncoredev.gestion_pedidos_api.model.Pedido;
import com.dragoncoredev.gestion_pedidos_api.model.Producto;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // --- RELACIÓN CON PRODUCTO (El "Artículo") ---
    // MUCHOS PedidoItems (Many) pueden referirse a UN Producto (One).
    // (Ej: 5 líneas de pedido diferentes, en 5 pedidos distintos,
    // pueden estar apuntando todas al producto "Tornillo 5mm").
    //
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;


    // --- CAMPO DE DATOS PROPIO ---
    // Esta es la información que esta entidad aporta a la relación.
    @Column(nullable = false)
    private Integer cantidad;

}