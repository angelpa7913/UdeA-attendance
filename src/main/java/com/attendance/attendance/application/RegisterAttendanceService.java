package com.attendance.attendance.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code RegisterAttendanceUseCase}: ValidateToken → CheckEnrollment → save.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: orquesta; cada verificación vive en el módulo dueño.<br>
 * D: depende de las api de session y enrollment, no de sus clases internas.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: token crudo, correo del participante.</li>
 *   <li>Salida: {@code RegistrationResult}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code ValidateTokenUseCase}, {@code CheckEnrollmentUseCase}, {@code AttendanceRepository}, {@code Clock}.</li>
 *   <li>{@code validate(token)} vacío → {@code InvalidToken}.</li>
 *   <li>{@code !validated.open()} → {@code SessionClosed}.</li>
 *   <li>{@code findParticipantId(email, courseId)} vacío → {@code NotEnrolled}.</li>
 *   <li>{@code exists(...)} → {@code AlreadyRegistered}.</li>
 *   <li>{@code save(new AttendanceRecord(..., clock.instant()))}; si devuelve false (carrera) → {@code AlreadyRegistered}; si no → {@code Registered}.</li>
 *   <li>Cada paso es un retorno temprano: sin {@code if} anidados.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code session.api.ValidateTokenUseCase}</li>
 *   <li>{@code enrollment.api.CheckEnrollmentUseCase}</li>
 *   <li>{@code AttendanceRepository} (port.out)</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
public class RegisterAttendanceService {
}
