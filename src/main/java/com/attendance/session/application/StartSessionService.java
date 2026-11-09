package com.attendance.session.application;

import java.time.Clock;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.attendance.enrollment.api.ListInstructorCoursesUseCase;
import com.attendance.session.api.StartSessionResult;
import com.attendance.session.api.StartSessionUseCase;
import com.attendance.session.application.port.out.SecretGenerator;
import com.attendance.session.application.port.out.SessionRepository;
import com.attendance.session.domain.ClassSession;

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
@Service
public class StartSessionService implements StartSessionUseCase {

    private final ListInstructorCoursesUseCase listInstructorCourses;
    private final SessionRepository sessionRepository;
    private final SecretGenerator secretGenerator;
    private final Clock clock;

    public StartSessionService(ListInstructorCoursesUseCase listInstructorCourses,
            SessionRepository sessionRepository, SecretGenerator secretGenerator, Clock clock) {
        this.listInstructorCourses = listInstructorCourses;
        this.sessionRepository = sessionRepository;
        this.secretGenerator = secretGenerator;
        this.clock = clock;
    }

    @Override
    public StartSessionResult start(long courseId, String instructorEmail) {
        // SECURITY: solo el instructor del curso puede abrirle sesiones.
        if (listInstructorCourses.coursesOf(instructorEmail).stream().noneMatch(c -> c.id() == courseId)) {
            return new StartSessionResult.NotCourseOwner();
        }
        final Optional<ClassSession> open = sessionRepository.findOpenByCourse(courseId);
        if (open.isPresent()) {
            return new StartSessionResult.AlreadyOpen(open.get().id().orElseThrow());
        }
        final ClassSession session = sessionRepository.save(
                ClassSession.open(courseId, instructorEmail, secretGenerator.newSecret(), clock.instant()));
        return new StartSessionResult.Started(session.id().orElseThrow());
    }
}
