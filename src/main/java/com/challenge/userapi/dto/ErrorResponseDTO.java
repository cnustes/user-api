package com.challenge.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO estándar para devolver mensajes de error en la API.
 */
@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String mensaje;
}