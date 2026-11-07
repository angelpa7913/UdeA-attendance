package com.attendance.enrollment.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Repositorio Spring Data de {@code EnrollmentJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: acceso a una tabla. Solo lo usa {@code EnrollmentPersistenceAdapter} (nunca un servicio ni un controlador).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Métodos sugeridos: consulta con JOIN a participants por email y course_id.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Extender {@code JpaRepository<EnrollmentJpaEntity, ...>} con el tipo de id correcto.</li>
 *   <li>Preferir métodos derivados; si usas {@code @Query}, que sea JPQL (portable entre motores).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentJpaEntity}</li>
 * </ul>
 */
public interface EnrollmentSpringDataRepository {
}
