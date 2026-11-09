package com.attendance.enrollment.domain;

import java.util.Locale;
import java.util.Optional;

/**
 * <h2>Propósito</h2>
 * Entidad de dominio: un curso, grupo, evento o capacitación con su instructor.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: sabe si un correo es su instructor. Nombres genéricos.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Atributos sugeridos: {@code Long id}, {@code String code}, {@code String group}, {@code String name}, {@code String instructorEmail}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Atributos {@code private final}.</li>
 *   <li>Método {@code boolean isInstructedBy(String email)} (comparación sin distinguir mayúsculas).</li>
 *   <li>{@code code + group} es la clave natural: documentarlo (habrá restricción UNIQUE en la tabla).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public final class Course {

    private final Long id;
    private final String code;
    private final String group;
    private final String name;
    private final String instructorEmail;

    private Course(Long id, String code, String group, String name, String instructorEmail) {
        if (isBlank(code) || isBlank(group) || isBlank(name) || isBlank(instructorEmail)) {
            throw new IllegalArgumentException("Código, grupo, nombre e instructor son obligatorios");
        }
        this.id = id;
        this.code = code.strip();
        this.group = group.strip();
        this.name = name.strip();
        this.instructorEmail = instructorEmail.strip().toLowerCase(Locale.ROOT);
    }

    /** Crea un curso sin id. La clave natural es código + grupo (UNIQUE en la tabla). */
    public static Course create(String code, String group, String name, String instructorEmail) {
        return new Course(null, code, group, name, instructorEmail);
    }

    public static Course restore(long id, String code, String group, String name, String instructorEmail) {
        return new Course(id, code, group, name, instructorEmail);
    }

    public boolean isInstructedBy(String email) {
        return instructorEmail.equalsIgnoreCase(email);
    }

    public Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public String code() {
        return code;
    }

    public String group() {
        return group;
    }

    public String name() {
        return name;
    }

    public String instructorEmail() {
        return instructorEmail;
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
