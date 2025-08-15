package com.challenge.userapi.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para la respuesta de una operación de usuario exitosa.
 * Muestra los datos que se devuelven al cliente.
 */
@Data
public class UserResponseDTO {

    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
}