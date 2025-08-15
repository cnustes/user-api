package com.challenge.userapi.repository;

import com.challenge.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la entidad User.
 * Proporciona métodos CRUD y consultas personalizadas para interactuar con la base de datos.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Spring Data JPA implementa este método automáticamente basándose en el nombre.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return un Optional que contiene al usuario si se encuentra, o un Optional vacío si no.
     */
    Optional<User> findByEmail(String email);
}