package com.attendance.identity.infrastructure.security;

/**
 * <h2>Propósito</h2>
 * Implementa {@code AuthenticatedUserProvider} leyendo el {@code OidcUser} del {@code SecurityContext}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable; es lo único que sabe de Spring Security.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code SecurityContextHolder} (implícito).</li>
 *   <li>Salida: {@code Optional} con sub, email y nombre.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Component} e implementar el puerto.</li>
 *   <li>Leer {@code Authentication}; si es nula o su principal no es {@code OidcUser}, devolver vacío.</li>
 *   <li>Mapear {@code getSubject()}, {@code getEmail()}, {@code getFullName()} (si falta el nombre, usar el email).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AuthenticatedUserProvider} (port.out)</li>
 * </ul>
 */
public class OidcAuthenticatedUserProvider {
}
