package com.attendance.identity.domain;

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
}
