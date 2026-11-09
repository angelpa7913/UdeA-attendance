package com.attendance.enrollment.application.port.out;

import java.util.List;
import java.util.Optional;

import com.attendance.enrollment.domain.Course;
import com.attendance.enrollment.domain.Enrollment;
import com.attendance.enrollment.domain.Participant;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: persistencia de participantes, cursos e inscripciones.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I: solo lo que los servicios usan. D: los servicios no saben si es JPA o memoria.<br>
 * L: el adaptador JPA y uno en memoria (pruebas) deben comportarse igual.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Métodos sugeridos:</li>
 *   <li>{@code Optional<Long> findParticipantIdEnrolled(String email, long courseId)}</li>
 *   <li>{@code List<Course> findCoursesByInstructor(String email)}</li>
 *   <li>{@code Participant saveParticipantIfAbsent(Participant p)} (por email)</li>
 *   <li>{@code Course saveCourseIfAbsent(Course c)} (por código+grupo)</li>
 *   <li>{@code boolean enroll(Enrollment e)} (false si ya existía)</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar los métodos con tipos de dominio (nunca entidades JPA).</li>
 *   <li>Nunca devolver null: {@code Optional} o listas vacías.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code Participant}, {@code Course}, {@code Enrollment} (domain)</li>
 * </ul>
 */
public interface EnrollmentRepository {

    Optional<Long> findParticipantIdEnrolled(String email, long courseId);

    List<Course> findCoursesByInstructor(String email);

    Participant saveParticipantIfAbsent(Participant participant);

    Course saveCourseIfAbsent(Course course);

    /** Devuelve {@code false} si la inscripción ya existía. */
    boolean enroll(Enrollment enrollment);
}
