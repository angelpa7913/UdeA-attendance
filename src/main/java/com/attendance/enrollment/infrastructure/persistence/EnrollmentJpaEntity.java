package com.attendance.enrollment.infrastructure.persistence;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code enrollments}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo objeto-relacional. Distinta de la entidad de dominio; se unen con {@code EnrollmentMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: PK compuesta ({@code participantId}, {@code courseId}) con {@code @IdClass} o {@code @EmbeddedId}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Entity} y {@code @Table(name = "enrollments")}.</li>
 *   <li>Constructor protegido sin argumentos (lo exige JPA) y getters; evitar setters públicos innecesarios.</li>
 *   <li>Los nombres de columna deben coincidir con la migración Flyway (snake_case).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence. Nada de otros módulos.</li>
 * </ul>
 */
@Entity
@Table(name = "enrollments")
@IdClass(EnrollmentJpaEntity.Key.class)
public class EnrollmentJpaEntity {

    @Id
    @Column(name = "participant_id")
    private Long participantId;

    @Id
    @Column(name = "course_id")
    private Long courseId;

    protected EnrollmentJpaEntity() {
    }

    public EnrollmentJpaEntity(Long participantId, Long courseId) {
        this.participantId = participantId;
        this.courseId = courseId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Long getCourseId() {
        return courseId;
    }

    /** Clave primaria compuesta de la inscripción. */
    public static class Key implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long participantId;
        private Long courseId;

        protected Key() {
        }

        public Key(Long participantId, Long courseId) {
            this.participantId = participantId;
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Key key
                    && Objects.equals(participantId, key.participantId)
                    && Objects.equals(courseId, key.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(participantId, courseId);
        }
    }
}
