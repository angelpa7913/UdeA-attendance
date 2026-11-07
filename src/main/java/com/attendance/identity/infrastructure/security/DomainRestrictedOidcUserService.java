package com.attendance.identity.infrastructure.security;

/**
 * <h2>Propósito</h2>
 * Carga el usuario OIDC y rechaza el login si el dominio del correo no está permitido.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo filtra por dominio; la regla está en {@code EmailDomainPolicy}.<br>
 * L: es un {@code OidcUserService} sustituible por el de Spring.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code OidcUserRequest}.</li>
 *   <li>Salida: {@code OidcUser} o {@code OAuth2AuthenticationException} (es la forma que Spring espera para rechazar).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Component}; delegar en un {@code OidcUserService} interno (composición, no herencia).</li>
 *   <li>Constructor con {@code AttendanceProperties}; crear la {@code EmailDomainPolicy} con {@code allowedEmailDomains()}.</li>
 *   <li>{@code loadUser}: delegar, leer el claim {@code email} y crear {@code EmailAddress}.</li>
 *   <li>// SECURITY: exigir {@code email_verified == true} si el proveedor lo envía.</li>
 *   <li>// SECURITY: si el dominio no está permitido, lanzar {@code OAuth2AuthenticationException} con un código claro.</li>
 *   <li>Devolver el {@code OidcUser} original.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EmailAddress}, {@code EmailDomainPolicy} (domain)</li>
 *   <li>{@code AttendanceProperties} (shared)</li>
 * </ul>
 */
public class DomainRestrictedOidcUserService {
}
