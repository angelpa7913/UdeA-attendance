package com.attendance.session.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Convierte {@code ClassSession} ↔ {@code SessionJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo conversión.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code toDomain(SessionJpaEntity)}, {@code toJpa(ClassSession)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Métodos estáticos y constructor privado, o {@code @Component}.</li>
 *   <li>{@code closedAt} nulo en BD ↔ {@code Optional.empty()} en dominio.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ClassSession}, {@code SessionJpaEntity}</li>
 * </ul>
 */
public final class SessionMapper {
}
