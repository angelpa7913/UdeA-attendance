package com.attendance.identity.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code GetCurrentUserUseCase}: arma el {@code CurrentUser} con su rol.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso por servicio.<br>
 * D: recibe sus puertos por constructor; se prueba sin Spring.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: ninguna.</li>
 *   <li>Salida: {@code Optional<CurrentUser>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code implements GetCurrentUserUseCase} y anotar con {@code @Service}.</li>
 *   <li>Constructor con: {@code AuthenticatedUserProvider}, {@code InstructorDirectory}.</li>
 *   <li>Pedir el usuario autenticado; si está vacío, devolver {@code Optional.empty()} (retorno temprano).</li>
 *   <li>Rol = {@code INSTRUCTOR} si {@code instructorDirectory.isInstructor(email)}, si no {@code PARTICIPANT}.</li>
 *   <li>Construir y devolver el {@code CurrentUser}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AuthenticatedUserProvider} (port.out)</li>
 *   <li>{@code InstructorDirectory} (api)</li>
 *   <li>{@code CurrentUser}, {@code Role}</li>
 * </ul>
 */
public class GetCurrentUserService {
}
