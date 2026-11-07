package com.attendance.enrollment.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code CheckEnrollmentUseCase}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso. D: recibe el repositorio por constructor.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: email, courseId.</li>
 *   <li>Salida: {@code Optional<Long>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}, {@code implements CheckEnrollmentUseCase}.</li>
 *   <li>Constructor con {@code EnrollmentRepository}.</li>
 *   <li>Normalizar el email y delegar en {@code findParticipantIdEnrolled}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentRepository} (port.out)</li>
 * </ul>
 */
public class CheckEnrollmentService {
}
