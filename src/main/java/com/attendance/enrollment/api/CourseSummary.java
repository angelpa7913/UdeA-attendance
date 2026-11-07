package com.attendance.enrollment.api;

/**
 * <h2>Propósito</h2>
 * DTO inmutable con lo que otros módulos necesitan saber de un curso.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo datos; no expone la entidad {@code Course}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes sugeridos: {@code long id}, {@code String code}, {@code String group}, {@code String name}, {@code String instructorEmail}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes.</li>
 *   <li>Opcional: método {@code label()} que devuelva {@code code + "-" + group} para las vistas.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public record CourseSummary() {
}
