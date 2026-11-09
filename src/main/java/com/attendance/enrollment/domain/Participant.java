package com.attendance.enrollment.domain;

import java.util.Locale;
import java.util.Optional;

/**
 * <h2>Propósito</h2>
 * Entidad de dominio: una persona que puede inscribirse (estudiante, asistente, empleado...).
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: reglas de un participante. Sin JPA (la entidad JPA es otra clase).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Atributos sugeridos: {@code Long id} (puede ser nulo antes de guardar → usa {@code Optional<Long> id()}), {@code String email}, {@code String fullName}, {@code String externalId} (documento o código institucional, opcional).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Atributos {@code private final}.</li>
 *   <li>Constructor que normalice el email a minúsculas y rechace valores en blanco.</li>
 *   <li>Fábrica {@code static Participant create(email, fullName, externalId)} sin id; otra para reconstruir desde BD con id.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared.domain} si necesitas {@code DomainError}.</li>
 * </ul>
 */
public final class Participant {

    private final Long id;
    private final String email;
    private final String fullName;
    private final String externalId;

    private Participant(Long id, String email, String fullName, String externalId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo del participante no puede estar en blanco");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("El nombre del participante no puede estar en blanco");
        }
        this.id = id;
        this.email = email.strip().toLowerCase(Locale.ROOT);
        this.fullName = fullName.strip();
        this.externalId = externalId == null || externalId.isBlank() ? null : externalId.strip();
    }

    public static Participant create(String email, String fullName, String externalId) {
        return new Participant(null, email, fullName, externalId);
    }

    public static Participant restore(long id, String email, String fullName, String externalId) {
        return new Participant(id, email, fullName, externalId);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String email() {
        return email;
    }

    public String fullName() {
        return fullName;
    }

    public Optional<String> externalId() {
        return Optional.ofNullable(externalId);
    }
}
