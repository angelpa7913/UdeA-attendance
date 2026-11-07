package com.attendance.enrollment.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code participants}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo objeto-relacional. Distinta de la entidad de dominio; se unen con {@code EnrollmentMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: {@code id} (identity), {@code email} (único), {@code fullName}, {@code externalId}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Entity} y {@code @Table(name = "participants")}.</li>
 *   <li>Constructor protegido sin argumentos (lo exige JPA) y getters; evitar setters públicos innecesarios.</li>
 *   <li>Los nombres de columna deben coincidir con la migración Flyway (snake_case).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence. Nada de otros módulos.</li>
 * </ul>
 */
public class ParticipantJpaEntity {
}
