package com.attendance.enrollment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attendance.enrollment.api.CourseSummary;
import com.attendance.enrollment.api.ListInstructorCoursesUseCase;
import com.attendance.enrollment.application.port.out.EnrollmentRepository;
import com.attendance.enrollment.domain.Course;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ListInstructorCoursesUseCase}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: una consulta. Convierte {@code Course} (dominio) a {@code CourseSummary} (api).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: correo del instructor.</li>
 *   <li>Salida: {@code List<CourseSummary>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code EnrollmentRepository}.</li>
 *   <li>Consultar y mapear cada {@code Course} a {@code CourseSummary}; lista inmutable ({@code toList()}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentRepository} (port.out)</li>
 * </ul>
 */
@Service
public class ListInstructorCoursesService implements ListInstructorCoursesUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public ListInstructorCoursesService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<CourseSummary> coursesOf(String instructorEmail) {
        return enrollmentRepository.findCoursesByInstructor(instructorEmail).stream()
                .map(ListInstructorCoursesService::toSummary)
                .toList();
    }

    private static CourseSummary toSummary(Course course) {
        return new CourseSummary(course.id().orElseThrow(), course.code(), course.group(), course.name(),
                course.instructorEmail());
    }
}
