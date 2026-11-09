package com.attendance.session.infrastructure.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.attendance.session.application.port.out.TokenSigner;

/**
 * <h2>Propósito</h2>
 * Implementa {@code TokenSigner} con HMAC-SHA256 y Base64 URL-safe.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/L: solo criptografía; reemplazable por otro algoritmo sin tocar servicios.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code sign(data, secret)} → Base64 URL-safe sin relleno.</li>
 *   <li>{@code verify(data, signature, secret)} → boolean.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements TokenSigner}. Nombre del algoritmo {@code "HmacSHA256"} como constante.</li>
 *   <li>{@code sign}: {@code Mac.getInstance}, {@code init(new SecretKeySpec(secretBytes, ALG))}, {@code doFinal(data UTF-8)}, codificar con {@code Base64.getUrlEncoder().withoutPadding()}.</li>
 *   <li>// SECURITY: {@code verify} recalcula la firma y compara bytes con {@code MessageDigest.isEqual} (tiempo constante), nunca {@code equals}.</li>
 *   <li>Las excepciones checked de JCA ({@code NoSuchAlgorithmException}, {@code InvalidKeyException}) se envuelven en {@code IllegalStateException} con causa: no son flujo normal.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code TokenSigner} (port.out)</li>
 *   <li>{@code javax.crypto}, {@code java.security}</li>
 * </ul>
 */
@Component
public class HmacTokenSigner implements TokenSigner {

    private static final String ALGORITHM = "HmacSHA256";

    @Override
    public String sign(String data, String secret) {
        try {
            final Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("No se pudo firmar el token", e);
        }
    }

    @Override
    public boolean verify(String data, String signature, String secret) {
        // SECURITY: comparación en tiempo constante, nunca equals.
        return MessageDigest.isEqual(sign(data, secret).getBytes(StandardCharsets.UTF_8),
                signature.getBytes(StandardCharsets.UTF_8));
    }
}
