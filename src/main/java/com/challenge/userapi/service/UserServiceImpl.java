package com.challenge.userapi.service;

import com.challenge.userapi.dto.UserRequestDTO;
import com.challenge.userapi.dto.UserResponseDTO;
import com.challenge.userapi.exception.EmailAlreadyExistsException;
import com.challenge.userapi.model.Phone;
import com.challenge.userapi.model.User;
import com.challenge.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Importamos la anotación
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación de la lógica de negocio para la gestión de usuarios.
 */
@Service
@RequiredArgsConstructor
@Slf4j // Anotación de Lombok para inyectar el logger
public class UserServiceImpl implements UserService {

    // Inyección de dependencias a través del constructor gracias a Lombok @RequiredArgsConstructor
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Log al inicio del método con un parámetro para trazabilidad.
        log.info("Iniciando proceso de creación para el correo: {}", userRequestDTO.getEmail());

        // 1. Validar si el correo ya existe
        userRepository.findByEmail(userRequestDTO.getEmail())
                .ifPresent(user -> {
                    // Log de advertencia antes de lanzar la excepción
                    log.warn("⚠️  Intento de registro con correo duplicado: {}", userRequestDTO.getEmail());
                    throw new EmailAlreadyExistsException("El correo ya registrado");
                });

        // 2. Mapear el DTO de solicitud a la entidad User
        User user = mapToUserEntity(userRequestDTO);

        // 3. Guardar el nuevo usuario en la base de datos
        User savedUser = userRepository.save(user);
        log.info("✅  Usuario guardado exitosamente en la BD con ID: {}", savedUser.getId());

        // 4. Mapear la entidad guardada a un DTO de respuesta
        return mapToUserResponseDTO(savedUser);
    }

    /**
     * Método privado para convertir el DTO de solicitud a la entidad User.
     */
    private User mapToUserEntity(UserRequestDTO requestDTO) {
        log.debug("➡️  Mapeando UserRequestDTO a la entidad User para el correo: {}", requestDTO.getEmail());
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        // Codificamos la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // Mapeamos la lista de teléfonos
        if (requestDTO.getPhones() != null) {
            user.setPhones(requestDTO.getPhones().stream().map(phoneDTO -> {
                Phone phone = new Phone();
                phone.setNumber(phoneDTO.getNumber());
                phone.setCitycode(phoneDTO.getCitycode());
                phone.setCountrycode(phoneDTO.getCountrycode());
                phone.setUser(user);
                return phone;
            }).collect(Collectors.toList()));
        }
        // 4. Generar y asignar el token
        user.setToken(UUID.randomUUID().toString());

        // 5. Establecer la fecha de último login y el estado activo
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        // La fecha de creación (created) y modificación (modified) se gestionan automáticamente con @CreationTimestamp y @UpdateTimestamp


        return user;
    }

    /**
     * Método privado para convertir la entidad User a un DTO de respuesta.
     */
    private UserResponseDTO mapToUserResponseDTO(User user) {
        log.debug("➡️  Mapeando entidad User a UserResponseDTO para el ID: {}", user.getId());
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setCreated(user.getCreated());
        responseDTO.setModified(user.getModified());
        responseDTO.setLastLogin(user.getLastLogin());
        responseDTO.setToken(user.getToken());
        responseDTO.setActive(user.isActive());
        return responseDTO;
    }
}