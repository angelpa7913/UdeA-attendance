package com.attendance.attendance.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Implementa {@code AttendanceRepository} con Spring Data.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Las del puerto.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements AttendanceRepository}; constructor con el repositorio Spring Data.</li>
 *   <li>{@code save}: {@code saveAndFlush}; capturar {@code DataIntegrityViolationException} (específica) → devolver false.</li>
 *   <li>No usar {@code save} a secas para insertar con PK conocida: JPA haría un merge silencioso. Usa {@code persist} vía {@code EntityManager} o implementa {@code Persistable} con {@code isNew() = true}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AttendanceRepository} (port.out)</li>
 *   <li>{@code AttendanceRecordSpringDataRepository}, {@code AttendanceRecordMapper}</li>
 * </ul>
 */
public class AttendancePersistenceAdapter {
}
