package com.attendance.identity.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: obtener el usuario autenticado de la petición actual.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: un solo caso de uso, una sola operación.<br>
 * D: controladores de otros módulos dependen de esta interfaz, no de Spring Security.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: ninguna (el contexto de la petición).</li>
 *   <li>Salida: {@code Optional<CurrentUser>} (vacío si no hay sesión). Nunca null.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code Optional<CurrentUser> currentUser();}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code CurrentUser}</li>
 * </ul>
 */
public interface GetCurrentUserUseCase {
}
