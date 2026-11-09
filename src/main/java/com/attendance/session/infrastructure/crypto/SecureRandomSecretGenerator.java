package com.attendance.session.infrastructure.crypto;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.attendance.session.application.port.out.SecretGenerator;

/**
 * <h2>Propósito</h2>
 * Implementa {@code SecretGenerator} con {@code SecureRandom}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/L: una responsabilidad; en pruebas se reemplaza por uno fijo.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Salida: secreto de 32 bytes en Base64.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}. Atributo {@code private final SecureRandom random = new SecureRandom();}.</li>
 *   <li>Longitud en bytes como constante con nombre (32 = 256 bits).</li>
 *   <li>// SECURITY: el secreto lo genera la aplicación (no la BD) y nunca se envía al navegador.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SecretGenerator} (port.out)</li>
 * </ul>
 */
@Component
public class SecureRandomSecretGenerator implements SecretGenerator {

    private static final int SECRET_BYTES = 32;

    private final SecureRandom random = new SecureRandom();

    // SECURITY: el secreto lo genera la aplicación y nunca se envía al navegador.
    @Override
    public String newSecret() {
        final byte[] secret = new byte[SECRET_BYTES];
        random.nextBytes(secret);
        return Base64.getEncoder().encodeToString(secret);
    }
}
