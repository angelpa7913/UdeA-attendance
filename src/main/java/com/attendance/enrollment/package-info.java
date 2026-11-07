/**
 * <h2>Propósito</h2>
 * Módulo de inscripciones: participantes, cursos (o eventos) y quién está inscrito en qué.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: dueño de las tablas {@code participants}, {@code courses}, {@code enrollments}.<br>
 * O: nuevas fuentes de inscritos = nueva implementación de {@code EnrollmentSource}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone (api): {@code CheckEnrollmentUseCase}, {@code ImportEnrollmentsUseCase}, {@code ListInstructorCoursesUseCase}, {@code CourseSummary}.</li>
 *   <li>Implementa: {@code InstructorDirectory} de identity.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Implementar {@code domain} → {@code application} → {@code infrastructure/persistence} → {@code csv} → {@code web}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared}, {@code identity.api}.</li>
 * </ul>
 */
@org.springframework.modulith.ApplicationModule(displayName = "Enrollment")
package com.attendance.enrollment;
