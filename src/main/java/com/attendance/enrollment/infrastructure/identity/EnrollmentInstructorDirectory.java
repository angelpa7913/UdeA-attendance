package com.attendance.enrollment.infrastructure.identity;

/**
 * <h2>Propósito</h2>
 * Implementa {@code InstructorDirectory} (de identity): es instructor quien tenga al menos un curso a su nombre.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: enrollment cumple el contrato que identity definió; identity no conoce enrollment.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: email.</li>
 *   <li>Salida: {@code boolean}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements InstructorDirectory}.</li>
 *   <li>Constructor con {@code ListInstructorCoursesUseCase}.</li>
 *   <li>Devolver {@code !coursesOf(email).isEmpty()}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.InstructorDirectory}</li>
 *   <li>{@code ListInstructorCoursesUseCase} (api propia)</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: resuelve el rol sin crear un ciclo identity ↔ enrollment.<br>
 */
public class EnrollmentInstructorDirectory {
}
