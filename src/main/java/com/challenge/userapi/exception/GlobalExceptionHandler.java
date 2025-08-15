package com.challenge.userapi.exception;

import com.challenge.userapi.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador de excepciones global para toda la aplicación.
 * Captura excepciones específicas y las formatea en una respuesta HTTP estándar.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción de correo duplicado.
     * Se activa cuando el servicio lanza EmailAlreadyExistsException.
     * @param ex La excepción capturada.
     * @return una respuesta con código 409 Conflict y el mensaje de error.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        log.error("❌  Error de negocio: {}", ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja las excepciones de validación de los DTOs.
     * Se activa cuando una validación en un @RequestBody anotado con @Valid falla.
     * @param ex La excepción capturada.
     * @return una respuesta con código 400 Bad Request y el mensaje de error de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Obtenemos el primer error de validación para simplificar el mensaje
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");

        log.error("❌  Error de validación: {}", errorMessage);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Un manejador genérico para cualquier otra excepción no controlada.
     * Esto asegura que la API nunca devuelva un error HTML de Spring por defecto.
     * @param ex La excepción capturada.
     * @return una respuesta con código 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex) {
        log.error("❌  Error interno no controlado: {}", ex.getMessage(), ex);
        ErrorResponseDTO errorResponse = new ErrorResponseDTO("Ocurrió un error inesperado en el servidor");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}