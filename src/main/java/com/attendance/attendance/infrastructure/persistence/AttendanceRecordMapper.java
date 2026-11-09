package com.attendance.attendance.infrastructure.persistence;

import com.attendance.attendance.domain.AttendanceRecord;

/**
 * <h2>Propósito</h2>
 * Convierte {@code AttendanceRecord} ↔ {@code AttendanceRecordJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo conversión.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code toDomain}, {@code toJpa}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Métodos estáticos y constructor privado, o {@code @Component}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code AttendanceRecord}, {@code AttendanceRecordJpaEntity}</li>
 * </ul>
 */
public final class AttendanceRecordMapper {

    private AttendanceRecordMapper() {
    }

    static AttendanceRecord toDomain(AttendanceRecordJpaEntity entity) {
        return new AttendanceRecord(entity.getSessionId(), entity.getParticipantId(), entity.getRegisteredAt());
    }

    static AttendanceRecordJpaEntity toJpa(AttendanceRecord attendanceRecord) {
        return new AttendanceRecordJpaEntity(attendanceRecord.sessionId(), attendanceRecord.participantId(),
                attendanceRecord.registeredAt());
    }
}
