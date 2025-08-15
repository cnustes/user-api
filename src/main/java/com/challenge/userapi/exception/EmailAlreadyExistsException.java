package com.challenge.userapi.exception;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un correo que ya existe en la base de datos.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}