package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * DTO: resultado de validar un token.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo datos para attendance; nunca incluye el secreto.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes: {@code long sessionId}, {@code long courseId}, {@code boolean open}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes.</li>
 *   <li>// SECURITY: no agregar el secreto ni la firma a este DTO.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public record ValidatedSession() {
}
