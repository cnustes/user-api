package com.challenge.userapi.controller;

import com.challenge.userapi.dto.UserRequestDTO;
import com.challenge.userapi.dto.UserResponseDTO;
import com.challenge.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para las operaciones de usuario.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param userRequestDTO El cuerpo de la solicitud con los datos del usuario.
     * @return una respuesta con el usuario creado y el estado HTTP 201 (Created).
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        log.info("▶️  Solicitud recibida para registrar usuario con correo: {}", userRequestDTO.getEmail());

        // Llama al servicio para ejecutar la lógica de negocio
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);

        log.info("✅  Usuario registrado exitosamente con ID: {}", createdUser.getId());

        // Devuelve el DTO de respuesta con el código de estado 201 Created
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}