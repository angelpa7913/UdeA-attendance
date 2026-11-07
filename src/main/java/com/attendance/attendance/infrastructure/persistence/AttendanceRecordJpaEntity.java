package com.attendance.attendance.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de {@code attendance_records}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: PK compuesta ({@code sessionId}, {@code participantId}), {@code registeredAt}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Entity}, {@code @Table(name = "attendance_records")}, {@code @IdClass(AttendanceRecordId.class)} o {@code @EmbeddedId}.</li>
 *   <li>Crear la clase de id (record serializable con {@code equals}/{@code hashCode}) en este paquete.</li>
 *   <li>Ids numéricos, sin relaciones JPA a tablas de otros módulos.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence</li>
 * </ul>
 */
public class AttendanceRecordJpaEntity {
}
