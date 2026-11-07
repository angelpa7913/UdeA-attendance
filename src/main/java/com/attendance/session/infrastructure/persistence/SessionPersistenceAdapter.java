package com.attendance.session.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Implementa {@code SessionRepository} con Spring Data.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Las del puerto.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements SessionRepository}; constructor con {@code SessionSpringDataRepository}.</li>
 *   <li>Convertir siempre con {@code SessionMapper}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionRepository} (port.out)</li>
 *   <li>{@code SessionSpringDataRepository}, {@code SessionMapper}</li>
 * </ul>
 */
public class SessionPersistenceAdapter {
}
