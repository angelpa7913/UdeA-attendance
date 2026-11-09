package com.attendance.attendance.infrastructure.persistence;

import java.io.Serializable;
import java.util.Objects;

/**
 * <h2>Propósito</h2>
 * Clave primaria compuesta de {@code attendance_records}.
 *
 * <h2>Depende de</h2>
 * Nada.
 */
public class AttendanceRecordId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sessionId;
    private Long participantId;

    protected AttendanceRecordId() {
    }

    public AttendanceRecordId(Long sessionId, Long participantId) {
        this.sessionId = sessionId;
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AttendanceRecordId id
                && Objects.equals(sessionId, id.sessionId)
                && Objects.equals(participantId, id.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, participantId);
    }
}
