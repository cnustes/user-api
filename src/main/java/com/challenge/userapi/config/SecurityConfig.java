package com.challenge.userapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración para beans y reglas de seguridad.
 */
@Configuration
@EnableWebSecurity // Habilita la personalización de la configuración de seguridad web
@Slf4j
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("⚙️  Configurando bean de PasswordEncoder (BCrypt)...");
        return new BCryptPasswordEncoder();
    }

    /**
     * Define la cadena de filtros de seguridad para configurar las reglas de acceso HTTP.
     * @param http el objeto HttpSecurity para configurar.
     * @return la cadena de filtros de seguridad construida.
     * @throws Exception si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("⚙️  Configurando la cadena de filtros de seguridad (SecurityFilterChain)...");

        http
                // 1. Deshabilitar Cross-Site Request Forgery (CSRF)
                .csrf(csrf -> csrf.disable())

                // 2. Definir las reglas de autorización para las solicitudes HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso sin autenticación a estas rutas específicas
                        .requestMatchers("/api/users/**", "/h2-console/**").permitAll()
                        // 3. Para cualquier otra solicitud, el usuario debe estar autenticado
                        .anyRequest().authenticated()
                );

        // Permite que la consola H2 se muestre en un frame (necesario para su UI)
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}