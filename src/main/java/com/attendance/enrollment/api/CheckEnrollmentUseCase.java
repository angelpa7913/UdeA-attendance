package com.attendance.enrollment.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: ¿el correo está inscrito en el curso?
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una pregunta. Lo usa attendance sin conocer las tablas de enrollment.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String email}, {@code long courseId}.</li>
 *   <li>Salida: {@code Optional<Long>} con el {@code participantId} (vacío si no está inscrito).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code Optional<Long> findParticipantId(String email, long courseId);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface CheckEnrollmentUseCase {
}
