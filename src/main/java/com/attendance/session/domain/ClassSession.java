package com.attendance.session.domain;

import java.time.Instant;
import java.util.Locale;
import java.util.Optional;

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

    private final Long id;
    private final long courseId;
    private final String instructorEmail;
    private final String secret;
    private final Instant openedAt;
    private final Instant closedAt;

    private ClassSession(Long id, long courseId, String instructorEmail, String secret, Instant openedAt,
            Instant closedAt) {
        this.id = id;
        this.courseId = courseId;
        this.instructorEmail = instructorEmail.strip().toLowerCase(Locale.ROOT);
        this.secret = secret;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }

    public static ClassSession open(long courseId, String instructorEmail, String secret, Instant now) {
        return new ClassSession(null, courseId, instructorEmail, secret, now, null);
    }

    public static ClassSession restore(long id, long courseId, String instructorEmail, String secret,
            Instant openedAt, Optional<Instant> closedAt) {
        return new ClassSession(id, courseId, instructorEmail, secret, openedAt, closedAt.orElse(null));
    }

    /** Cerrar una sesión ya cerrada la devuelve igual (idempotente). */
    public ClassSession close(Instant now) {
        return isOpen() ? new ClassSession(id, courseId, instructorEmail, secret, openedAt, now) : this;
    }

    public boolean isOpen() {
        return closedAt == null;
    }

    public boolean isOwnedBy(String email) {
        return instructorEmail.equalsIgnoreCase(email);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public long courseId() {
        return courseId;
    }

    public String instructorEmail() {
        return instructorEmail;
    }

    public String secret() {
        return secret;
    }

    public Instant openedAt() {
        return openedAt;
    }

    public Optional<Instant> closedAt() {
        return Optional.ofNullable(closedAt);
    }
}
