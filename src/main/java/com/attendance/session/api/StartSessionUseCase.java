package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: el instructor abre una sesión para uno de sus cursos.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una operación.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code long courseId}, {@code String instructorEmail}.</li>
 *   <li>Salida: resultado tipado — crea en api un {@code sealed interface StartSessionResult} con {@code Started(long sessionId)}, {@code NotCourseOwner}, {@code AlreadyOpen(long sessionId)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear {@code StartSessionResult} en este paquete.</li>
 *   <li>Declarar {@code StartSessionResult start(long courseId, String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface StartSessionUseCase {
}
