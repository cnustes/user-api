package com.challenge.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa el teléfono de un usuario.
 */
@Data
@Entity
@Table(name = "phones")
public class Phone {

    /**
     * Identificador único del teléfono.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * [cite_start]Número de teléfono. [cite: 15]
     */
    private String number;

    /**
     * [cite_start]Código de la ciudad. [cite: 16]
     */
    private String citycode;

    /**
     * [cite_start]Código del país. [cite: 17]
     */
    private String countrycode;

    /**
     * Relación con el usuario al que pertenece este teléfono.
     * Se usa FetchType.LAZY para no cargar el usuario a menos que sea explícitamente necesario.
     * JsonIgnore para evitar la recursividad infinita al serializar a JSON.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}