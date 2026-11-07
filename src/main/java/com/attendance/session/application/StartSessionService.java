package com.attendance.session.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code StartSessionUseCase}: verifica que el instructor sea dueño del curso y abre la sesión.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso. D: puertos por constructor.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: courseId, instructorEmail.</li>
 *   <li>Salida: {@code StartSessionResult}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code ListInstructorCoursesUseCase}, {@code SessionRepository}, {@code SecretGenerator}, {@code Clock}.</li>
 *   <li>// SECURITY: si el curso no está en {@code coursesOf(instructorEmail)} → {@code NotCourseOwner} (retorno temprano).</li>
 *   <li>Si ya hay una sesión abierta del curso → {@code AlreadyOpen(id)} (decide si reutilizarla).</li>
 *   <li>Crear {@code ClassSession.open(..., secretGenerator.newSecret(), clock.instant())}, guardar y devolver {@code Started(id)}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code enrollment.api.ListInstructorCoursesUseCase}</li>
 *   <li>{@code SessionRepository}, {@code SecretGenerator} (port.out)</li>
 *   <li>{@code java.time.Clock}</li>
 * </ul>
 */
public class StartSessionService {
}
