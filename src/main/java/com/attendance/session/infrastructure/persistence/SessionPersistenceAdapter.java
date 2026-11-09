package com.attendance.session.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.attendance.session.application.port.out.SessionRepository;
import com.attendance.session.domain.ClassSession;

/**
 * <h2>Propósito</h2>
 * Implementa {@code SessionRepository} con Spring Data.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Las del puerto.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements SessionRepository}; constructor con {@code SessionSpringDataRepository}.</li>
 *   <li>Convertir siempre con {@code SessionMapper}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionRepository} (port.out)</li>
 *   <li>{@code SessionSpringDataRepository}, {@code SessionMapper}</li>
 * </ul>
 */
@Component
public class SessionPersistenceAdapter implements SessionRepository {

    private final SessionSpringDataRepository sessions;

    public SessionPersistenceAdapter(SessionSpringDataRepository sessions) {
        this.sessions = sessions;
    }

    @Override
    public ClassSession save(ClassSession session) {
        return SessionMapper.toDomain(sessions.save(SessionMapper.toJpa(session)));
    }

    @Override
    public Optional<ClassSession> findById(long id) {
        return sessions.findById(id).map(SessionMapper::toDomain);
    }

    @Override
    public Optional<ClassSession> findOpenByCourse(long courseId) {
        return sessions.findFirstByCourseIdAndClosedAtIsNull(courseId).map(SessionMapper::toDomain);
    }
}
