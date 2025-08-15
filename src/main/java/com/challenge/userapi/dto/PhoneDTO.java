package com.challenge.userapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para representar la información de un teléfono en la solicitud de creación de usuario.
 */
@Data
public class PhoneDTO {

    /**
     * Número de teléfono.
     * No puede ser nulo o vacío.
     */
    @NotBlank(message = "El número no puede estar vacío")
    private String number;

    /**
     * Código de la ciudad.
     * No puede ser nulo o vacío.
     */
    @NotBlank(message = "El código de ciudad no puede estar vacío")
    private String citycode;

    /**
     * Código del país.
     * No puede ser nulo o vacío.
     */
    @NotBlank(message = "El código de país no puede estar vacío")
    private String countrycode;
}