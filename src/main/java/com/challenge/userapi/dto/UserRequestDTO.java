package com.challenge.userapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El formato del correo no es válido")
    private String email;

    /**
     * Contraseña del usuario.
     * La expresión regular está definida directamente aquí para evitar problemas con la inyección de propiedades en DTOs.
     * Exige: 1 mayúscula, 1 minúscula, 1 número, 1 caracter especial y de 8 a 15 caracteres.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
            message = "La contraseña debe tener entre 8 y 15 caracteres, incluyendo una mayúscula, una minúscula, un número y un caracter especial")
    private String password;

    @NotEmpty(message = "Debe proporcionar al menos un teléfono")
    private List<@Valid PhoneDTO> phones;
}