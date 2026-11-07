/**
 * <h2>Propósito</h2>
 * Módulo de asistencia: registrar que un participante estuvo en una sesión y contar asistentes.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: dueño de {@code attendance_records}. Orquesta session y enrollment solo a través de sus api.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone (api): {@code RegisterAttendanceUseCase}, {@code CountAttendeesUseCase}, {@code RegistrationResult}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Implementar {@code RegistrationResult} y {@code AttendanceRecord} primero; luego el servicio con sus pruebas.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared}, {@code identity.api}, {@code session.api}, {@code enrollment.api}.</li>
 * </ul>
 */
@org.springframework.modulith.ApplicationModule(displayName = "Attendance")
package com.attendance.attendance;
