package com.dragoncoredev.gestion_pedidos_api.model;

// Importamos nuestro Enum
import com.dragoncoredev.gestion_pedidos_api.model.PedidoItem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime; // La clase moderna de Java para fecha y hora
import java.util.ArrayList; // Importamos ArrayList para inicializar la lista
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    // ¡Buena práctica! Inicializamos el campo con la fecha/hora actual
    // en el momento en que se crea el objeto Pedido en Java.
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // NOTA DE ARQUITECTURA (MVP):
    // Para este MVP, ponemos el nombre del cliente como un simple String.
    // En una Iteración 2, esto debería ser una relación @ManyToOne
    // con una nueva entidad @Entity llamada 'Cliente'.
    @Column(nullable = false)
    private String nombreCliente;

    // --- ¡¡LECCIÓN DE MENTOR IMPORTANTE!! ---
    // Así mapeamos nuestro Enum 'EstadoPedido'.
    //
    // @Enumerated(EnumType.STRING) le dice a JPA que guarde en la BBDD
    // el *nombre* del enum (ej: "EN_ESPERA_PROVEEDOR").
    //
    // NUNCA uses el por defecto (@Enumerated(EnumType.ORDINAL)),
    // que guarda el *número* (0, 1, 2...). Si un día reordenas
    // tu Enum, ¡toda tu base de datos quedaría corrupta!
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    // Mapeo estándar de un booleano. Se convertirá en 'boolean' en PostgreSQL.
    // Lo inicializamos a 'false' por defecto.
    private boolean esPedidoInterno = false;

    // --- RELACIÓN CON PedidoItem ---
    // Un Pedido (One) tiene MUCHOS PedidoItems (Many).
    //
    // "mappedBy = 'pedido'":
    //     Le dice a JPA: "Yo (Pedido) no gestiono esta relación.
    //     El 'dueño' es el campo 'pedido' en la clase PedidoItem".
    //
    // "cascade = CascadeType.ALL", "orphanRemoval = true":
    //     ¡GESTIÓN DEL CICLO DE VIDA! Esto es clave.
    //     Significa: "Si yo (Pedido) me borro, borra en cascada
    //     todos mis 'PedidoItem' hijos. Si quito un 'PedidoItem'
    //     de esta lista, bórralo de la BBDD".
    //     Es una relación de "composición" (padre-hijo).
    //
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //
    // --- ¡¡MEJOR PRÁCTICA!! ---
    // Inicializamos la lista a una 'new ArrayList<>()'.
    // Esto evita que NUNCA tengamos un NullPointerException
    // si intentamos hacer 'pedido.getItems().add(...)'
    // antes de que la lista haya sido cargada desde la BBDD.
    private List<PedidoItem> items = new ArrayList<>();

}