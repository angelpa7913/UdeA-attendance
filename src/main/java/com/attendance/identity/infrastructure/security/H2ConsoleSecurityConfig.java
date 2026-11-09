package com.attendance.identity.infrastructure.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <h2>Propósito</h2>
 * Permite usar {@code /h2-console} en el perfil {@code dev}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo abre la consola de desarrollo; las reglas de la aplicación siguen en {@code SecurityConfig}.
 *
 * <h2>Depende de</h2>
 * Spring Security.
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: lo exige el criterio de "listo" de la etapa de migraciones.
 */
@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class H2ConsoleSecurityConfig {

    // SECURITY: esta cadena existe solo en dev; la consola no tiene login de la aplicación ni CSRF.
    @Bean
    @Order(0)
    SecurityFilterChain h2ConsoleFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(PathRequest.toH2Console())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .build();
    }
}
