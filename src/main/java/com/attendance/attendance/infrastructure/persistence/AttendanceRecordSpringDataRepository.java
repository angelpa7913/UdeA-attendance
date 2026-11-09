package com.attendance.attendance.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h2>Propósito</h2>
 * Repositorio Spring Data de {@code AttendanceRecordJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: acceso a la tabla; solo lo usa el adaptador.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code long countBySessionId(long sessionId)}</li>
 *   <li>{@code boolean existsBySessionIdAndParticipantId(long, long)}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Extender {@code JpaRepository<AttendanceRecordJpaEntity, AttendanceRecordId>}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AttendanceRecordJpaEntity}</li>
 * </ul>
 */
public interface AttendanceRecordSpringDataRepository
        extends JpaRepository<AttendanceRecordJpaEntity, AttendanceRecordId> {

    long countBySessionId(long sessionId);

    boolean existsBySessionIdAndParticipantId(long sessionId, long participantId);
}
