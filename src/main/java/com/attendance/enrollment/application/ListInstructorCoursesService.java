package com.attendance.enrollment.application;

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
public class ListInstructorCoursesService {
}
