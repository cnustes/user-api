package com.challenge.userapi.service;

import com.challenge.userapi.dto.UserRequestDTO;
import com.challenge.userapi.dto.UserResponseDTO;

/**
 * Interfaz para el servicio de gestión de usuarios.
 */
public interface UserService {

    /**
     * Crea un nuevo usuario en el sistema.
     * @param userRequestDTO DTO con la información del usuario a crear.
     * @return DTO con la información del usuario creado.
     */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}