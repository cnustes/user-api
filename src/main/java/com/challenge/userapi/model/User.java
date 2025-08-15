package com.challenge.userapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad que representa a un usuario en la base de datos.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Identificador único del usuario, generado como UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    /**
     * Nombre completo del usuario.
     */
    private String name;

    /**
     * Correo electrónico del usuario. Debe ser único en el sistema.
     */
    @Column(unique = true)
    private String email;

    /**
     * Contraseña del usuario. [Se almacenará de forma segura (hashed).
     */
    private String password;

    /**
     * Lista de teléfonos asociados al usuario.
     * CascadeType.ALL asegura que las operaciones (guardar, eliminar) en User se propaguen a Phone.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Phone> phones = new ArrayList<>();

    /**
     * Fecha y hora de creación del registro del usuario.
     * Se asigna automáticamente al crear la entidad.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    /**
     * Fecha y hora de la última modificación del registro del usuario.
     * Se actualiza automáticamente con cada modificación.
     */
    @UpdateTimestamp
    private LocalDateTime modified;

    /**
     * Fecha y hora del último inicio de sesión.
     * Para un usuario nuevo, coincide con la fecha de creación.
     */
    private LocalDateTime lastLogin;

    /**
     * Token de acceso generado para el usuario, que será persistido.
     */
    @Column(length = 512) // Aumentamos la longitud por si se usa un JWT
    private String token;

    /**
     * Indicador de si el usuario está activo o no en el sistema.
     */
    private boolean isActive;

}