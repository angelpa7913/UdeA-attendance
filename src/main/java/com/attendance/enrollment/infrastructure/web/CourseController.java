package com.attendance.enrollment.infrastructure.web;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: lista los cursos del instructor e importa inscritos.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida entrada, llama un caso de uso y responde. Nada de repositorios.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code GET /instructor/courses} → vista {@code instructor/courses}.</li>
 *   <li>{@code POST /instructor/courses/import} (multipart {@code file}) → redirige con el resumen.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code ListInstructorCoursesUseCase}, {@code ImportEnrollmentsUseCase}.</li>
 *   <li>Obtener el usuario actual; si está vacío, redirigir al login.</li>
 *   <li>GET: poner {@code courses} e {@code institutionName} en el {@code Model}.</li>
 *   <li>POST: llamar a {@code importEnrollments("csv", file.getInputStream(), email)}; mostrar el resumen con flash attributes.</li>
 *   <li>// SECURITY: el correo del instructor sale del usuario autenticado, nunca de un parámetro.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>api de enrollment</li>
 * </ul>
 */
public class CourseController {
}
