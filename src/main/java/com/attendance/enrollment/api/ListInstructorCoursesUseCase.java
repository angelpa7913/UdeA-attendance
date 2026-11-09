package com.attendance.enrollment.api;

import java.util.List;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: cursos de los que un correo es instructor.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una consulta. La usan {@code CourseController}, {@code StartSessionService} (para verificar propiedad) e {@code InstructorDirectory}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String instructorEmail}.</li>
 *   <li>Salida: {@code List<CourseSummary>} (vacía, nunca null).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code List<CourseSummary> coursesOf(String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code CourseSummary}</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: sin él, el controlador tendría que tocar el repositorio.<br>
 */
public interface ListInstructorCoursesUseCase {

    List<CourseSummary> coursesOf(String instructorEmail);
}
