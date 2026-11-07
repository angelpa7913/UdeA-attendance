package com.attendance.enrollment.application.port.out;

/**
 * <h2>Propósito</h2>
 * Fila neutral leída de una fuente: independiente del formato de origen.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo datos de transporte entre la fuente y el servicio.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes sugeridos: {@code String participantEmail}, {@code String participantName}, {@code String externalId}, {@code String courseCode}, {@code String courseGroup}, {@code String courseName}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes.</li>
 *   <li>No validar aquí; valida el servicio (así todas las fuentes se validan igual).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: es el tipo que devuelve {@code EnrollmentSource}.<br>
 */
public record EnrollmentRow() {
}
