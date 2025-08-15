package com.challenge.userapi.repository;

import com.challenge.userapi.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Phone.
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}