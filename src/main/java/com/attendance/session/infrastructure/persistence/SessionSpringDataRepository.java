package com.attendance.session.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Repositorio Spring Data de {@code SessionJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: acceso a la tabla; solo lo usa el adaptador.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code Optional<SessionJpaEntity> findFirstByCourseIdAndClosedAtIsNull(long courseId)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Extender {@code JpaRepository<SessionJpaEntity, Long>}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionJpaEntity}</li>
 * </ul>
 */
public interface SessionSpringDataRepository {
}
