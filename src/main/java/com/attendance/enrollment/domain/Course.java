package com.attendance.enrollment.domain;

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
}
