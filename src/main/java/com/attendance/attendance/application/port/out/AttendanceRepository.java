package com.attendance.attendance.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: persistencia de registros de asistencia.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I: tres operaciones. L: JPA y memoria intercambiables.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code boolean exists(long sessionId, long participantId)}</li>
 *   <li>{@code boolean save(AttendanceRecord r)} → false si ya existía (choque con la PK)</li>
 *   <li>{@code long countBySession(long sessionId)}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar los métodos.</li>
 *   <li>Documentar que {@code save} debe ser seguro ante carreras: la PK {@code (session_id, participant_id)} es la defensa final.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AttendanceRecord}</li>
 * </ul>
 */
public interface AttendanceRepository {
}
