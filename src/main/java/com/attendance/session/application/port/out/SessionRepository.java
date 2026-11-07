package com.attendance.session.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: persistencia de sesiones.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I: solo lo necesario. L: JPA y memoria intercambiables.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code ClassSession save(ClassSession s)} (devuelve con id)</li>
 *   <li>{@code Optional<ClassSession> findById(long id)}</li>
 *   <li>{@code Optional<ClassSession> findOpenByCourse(long courseId)}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar los métodos con tipos de dominio.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ClassSession}</li>
 * </ul>
 */
public interface SessionRepository {
}
