package com.attendance.identity.infrastructure.security;

/**
 * <h2>Propósito</h2>
 * Configura Spring Security: rutas públicas y protegidas, login OAuth2/OIDC y logout.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo reglas de acceso HTTP. La autorización fina (¿es dueño del curso?) vive en los casos de uso.<br>
 * O: el proveedor se define en YAML; aquí no aparece Google ni ningún otro.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code HttpSecurity}.</li>
 *   <li>Salida: bean {@code SecurityFilterChain}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Configuration} y {@code @EnableWebSecurity}.</li>
 *   <li>Bean {@code SecurityFilterChain filterChain(HttpSecurity http, DomainRestrictedOidcUserService userService)}.</li>
 *   <li>Públicas: {@code /}, {@code /css/**}, {@code /js/**}, {@code /error}. Todo lo demás: autenticado.</li>
 *   <li>{@code oauth2Login(o -> o.userInfoEndpoint(u -> u.oidcUserService(userService)))}.</li>
 *   <li>Al iniciar sesión, volver a la URL original (así {@code /attend?t=...} continúa tras el login).</li>
 *   <li>// SECURITY: mantener CSRF activo para formularios POST (iniciar/cerrar sesión, importar CSV).</li>
 *   <li>// SECURITY: configurar logout que invalide la sesión HTTP.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code DomainRestrictedOidcUserService}</li>
 * </ul>
 */
public class SecurityConfig {
}
