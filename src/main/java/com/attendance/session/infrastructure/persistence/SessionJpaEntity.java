package com.attendance.session.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code sessions}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo. Se convierte con {@code SessionMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: {@code id}, {@code courseId}, {@code instructorEmail}, {@code secret}, {@code openedAt}, {@code closedAt} (nullable).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Entity}, {@code @Table(name = "sessions")}.</li>
 *   <li>{@code courseId} es un número, no una relación JPA a {@code CourseJpaEntity} (cada módulo es dueño de sus tablas).</li>
 *   <li>Constructor protegido sin argumentos y getters.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence</li>
 * </ul>
 */
public class SessionJpaEntity {
}
