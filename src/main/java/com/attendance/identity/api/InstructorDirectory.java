package com.attendance.identity.api;

/**
 * <h2>Propósito</h2>
 * Puerto que identity necesita y que otro módulo (enrollment) implementa: ¿este correo es instructor de algún curso?
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: identity define la abstracción; enrollment la implementa. Así identity no conoce a enrollment.<br>
 * I: una sola pregunta.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: correo normalizado.</li>
 *   <li>Salida: {@code boolean}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code boolean isInstructor(String email);}.</li>
 *   <li>Documentar que la implementación debe ser de solo lectura.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Está en api porque la implementa otro módulo (es una SPI).<br>
 */
public interface InstructorDirectory {
}
