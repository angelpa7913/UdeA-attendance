package com.attendance.session.domain;

/**
 * <h2>Propósito</h2>
 * Entidad de dominio: una sesión de asistencia de un curso, con su ciclo de vida.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: reglas puras de abrir/cerrar. Sin Spring ni JPA. La hora llega como parámetro ({@code Instant}), nunca la lee sola.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Atributos sugeridos: {@code Long id}, {@code long courseId}, {@code String instructorEmail}, {@code String secret}, {@code Instant openedAt}, {@code Instant closedAt} (Optional).</li>
 *   <li>Métodos: {@code static ClassSession open(courseId, instructorEmail, secret, Instant now)}, {@code ClassSession close(Instant now)}, {@code boolean isOpen()}, {@code boolean isOwnedBy(String email)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Atributos {@code private final}; {@code close} devuelve una copia nueva (inmutable).</li>
 *   <li>{@code close} sobre una sesión ya cerrada: devolver la misma (idempotente) o un resultado; no lanzar excepción en el flujo normal.</li>
 *   <li>{@code isOpen()} = {@code closedAt} vacío.</li>
 *   <li>// SECURITY: no exponer {@code secret} en {@code toString()}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada (solo {@code java.time}).</li>
 * </ul>
 */
public final class ClassSession {
}
