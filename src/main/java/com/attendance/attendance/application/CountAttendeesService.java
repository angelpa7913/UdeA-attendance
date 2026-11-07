package com.attendance.attendance.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code CountAttendeesUseCase}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: una consulta.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sessionId.</li>
 *   <li>Salida: {@code long}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code AttendanceRepository}.</li>
 *   <li>Delegar en {@code countBySession}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AttendanceRepository} (port.out)</li>
 * </ul>
 */
public class CountAttendeesService {
}
