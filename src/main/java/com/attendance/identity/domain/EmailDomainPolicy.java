package com.attendance.identity.domain;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2>Propósito</h2>
 * Regla pura: ¿el dominio del correo está en la lista de dominios permitidos?
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: una regla, un archivo.<br>
 * O: los dominios llegan por constructor (desde configuración); la regla nunca cambia por institución.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada (constructor): {@code Collection<String> allowedDomains}.</li>
 *   <li>Entrada (método): {@code EmailAddress}.</li>
 *   <li>Salida: {@code boolean isAllowed(EmailAddress)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Atributo {@code private final Set<String> allowedDomains} (copia inmutable, en minúsculas).</li>
 *   <li>Constructor: rechazar lista vacía (sin dominios nadie podría entrar).</li>
 *   <li>{@code isAllowed}: {@code allowedDomains.contains(email.domain())}.</li>
 *   <li>Decidir si se aceptan subdominios (ej. {@code mail.udea.edu.co}): si sí, comparar con {@code endsWith("." + dominio)}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EmailAddress}. No recibe {@code AttendanceProperties} (el dominio no conoce Spring).</li>
 * </ul>
 */
public final class EmailDomainPolicy {

    private final Set<String> allowedDomains;

    public EmailDomainPolicy(Collection<String> allowedDomains) {
        if (allowedDomains == null || allowedDomains.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un dominio permitido");
        }
        this.allowedDomains = allowedDomains.stream()
                .map(domain -> domain.toLowerCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableSet());
    }

    public boolean isAllowed(EmailAddress email) {
        return allowedDomains.contains(email.domain());
    }
}
