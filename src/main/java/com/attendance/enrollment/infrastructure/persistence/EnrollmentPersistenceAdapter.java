package com.attendance.enrollment.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.attendance.enrollment.application.port.out.EnrollmentRepository;
import com.attendance.enrollment.domain.Course;
import com.attendance.enrollment.domain.Enrollment;
import com.attendance.enrollment.domain.Participant;

/**
 * <h2>Propósito</h2>
 * Implementa {@code EnrollmentRepository} con Spring Data.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador del puerto; intercambiable por uno en memoria.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada/salida: las del puerto, siempre en tipos de dominio.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements EnrollmentRepository}.</li>
 *   <li>Constructor con los tres repositorios Spring Data.</li>
 *   <li>Cada método: llamar al repositorio y convertir con {@code EnrollmentMapper}.</li>
 *   <li>{@code enroll}: comprobar existencia antes de insertar (la PK es la defensa final).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentRepository} (port.out)</li>
 *   <li>Repositorios Spring Data y {@code EnrollmentMapper}</li>
 * </ul>
 */
@Component
public class EnrollmentPersistenceAdapter implements EnrollmentRepository {

    private final ParticipantSpringDataRepository participants;
    private final CourseSpringDataRepository courses;
    private final EnrollmentSpringDataRepository enrollments;

    public EnrollmentPersistenceAdapter(ParticipantSpringDataRepository participants,
            CourseSpringDataRepository courses, EnrollmentSpringDataRepository enrollments) {
        this.participants = participants;
        this.courses = courses;
        this.enrollments = enrollments;
    }

    @Override
    public Optional<Long> findParticipantIdEnrolled(String email, long courseId) {
        return enrollments.findParticipantIdEnrolled(email, courseId);
    }

    @Override
    public List<Course> findCoursesByInstructor(String email) {
        return courses.findByInstructorEmail(email).stream().map(EnrollmentMapper::toDomain).toList();
    }

    @Override
    public Participant saveParticipantIfAbsent(Participant participant) {
        final ParticipantJpaEntity entity = participants.findByEmail(participant.email())
                .orElseGet(() -> participants.save(EnrollmentMapper.toJpa(participant)));
        return EnrollmentMapper.toDomain(entity);
    }

    @Override
    public Course saveCourseIfAbsent(Course course) {
        final CourseJpaEntity entity = courses.findByCodeAndGroupCode(course.code(), course.group())
                .orElseGet(() -> courses.save(EnrollmentMapper.toJpa(course)));
        return EnrollmentMapper.toDomain(entity);
    }

    @Override
    public boolean enroll(Enrollment enrollment) {
        final EnrollmentJpaEntity.Key key =
                new EnrollmentJpaEntity.Key(enrollment.participantId(), enrollment.courseId());
        if (enrollments.existsById(key)) {
            return false;
        }
        enrollments.save(new EnrollmentJpaEntity(enrollment.participantId(), enrollment.courseId()));
        return true;
    }
}
