package com.attendance.identity.domain;

/**
 * <h2>Propósito</h2>
 * Roles genéricos del sistema: sirven para clases, cursos, eventos o capacitaciones.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo enumera roles. Nombres neutrales (no "estudiante"/"profesor").<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Valores: {@code PARTICIPANT}, {@code INSTRUCTOR}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Ya declarado. Si Spring Security necesita la autoridad, agregar un método {@code authority()} que devuelva {@code "ROLE_" + name()}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public enum Role {
    PARTICIPANT,
    INSTRUCTOR
}
