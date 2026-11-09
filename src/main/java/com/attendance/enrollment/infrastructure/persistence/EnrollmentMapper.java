package com.attendance.enrollment.infrastructure.persistence;

import com.attendance.enrollment.domain.Course;
import com.attendance.enrollment.domain.Participant;

/**
 * <h2>Propósito</h2>
 * Convierte entre entidades de dominio y entidades JPA.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo conversión; así ni el dominio conoce JPA ni JPA conoce reglas.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Métodos sugeridos: {@code toDomain(ParticipantJpaEntity)}, {@code toJpa(Participant)}, y lo mismo para {@code Course}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Clase sin estado: métodos estáticos y constructor privado, o un {@code @Component}.</li>
 *   <li>Un método por dirección y tipo; sin lógica de negocio.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Entidades de dominio y JPA de este módulo.</li>
 * </ul>
 */
public final class EnrollmentMapper {

    private EnrollmentMapper() {
    }

    static Participant toDomain(ParticipantJpaEntity entity) {
        return Participant.restore(entity.getId(), entity.getEmail(), entity.getFullName(), entity.getExternalId());
    }

    static ParticipantJpaEntity toJpa(Participant participant) {
        return new ParticipantJpaEntity(participant.id().orElse(null), participant.email(), participant.fullName(),
                participant.externalId().orElse(null));
    }

    static Course toDomain(CourseJpaEntity entity) {
        return Course.restore(entity.getId(), entity.getCode(), entity.getGroupCode(), entity.getName(),
                entity.getInstructorEmail());
    }

    static CourseJpaEntity toJpa(Course course) {
        return new CourseJpaEntity(course.id().orElse(null), course.code(), course.group(), course.name(),
                course.instructorEmail());
    }
}
