package com.attendance.enrollment.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ImportEnrollmentsUseCase}: elige la fuente, valida filas y guarda.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: orquesta la importación. O: recibe {@code List<EnrollmentSource>} y elige con {@code supports} (sin if por formato).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sourceType, datos, correo del instructor.</li>
 *   <li>Salida: {@code ImportSummary}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code List<EnrollmentSource>}, {@code EnrollmentRepository}, {@code AttendanceProperties}.</li>
 *   <li>Buscar la fuente con {@code stream().filter(s -> s.supports(type)).findFirst()}; si no hay, resumen con error.</li>
 *   <li>Leer filas. Por cada una (bucle {@code for} sobre la lista, con fin claro): validar email y dominio permitido.</li>
 *   <li>// SECURITY: el instructor solo puede importar cursos donde él es el instructor; la fila no decide el instructor.</li>
 *   <li>Guardar participante, curso e inscripción; contar importadas y omitidas.</li>
 *   <li>Marcar el método con {@code @Transactional} (o hacerlo en el adaptador) para que una importación sea todo o nada.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentSource}, {@code EnrollmentRepository} (port.out)</li>
 *   <li>{@code AttendanceProperties} (shared)</li>
 * </ul>
 */
public class ImportEnrollmentsService {
}
