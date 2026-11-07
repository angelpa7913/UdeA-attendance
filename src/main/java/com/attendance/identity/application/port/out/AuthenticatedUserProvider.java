package com.attendance.identity.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: lee los datos crudos del usuario de la sesión HTTP actual.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: el servicio no conoce {@code SecurityContextHolder}; depende de esta interfaz.<br>
 * L: en pruebas se reemplaza por un fake en memoria.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: ninguna.</li>
 *   <li>Salida: {@code Optional<AuthenticatedUser>} con sub, email y nombre (define un record pequeño aquí o reutiliza {@code CurrentUser} sin rol).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code Optional<...> authenticatedUser();}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface AuthenticatedUserProvider {
}
