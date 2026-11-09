package com.attendance.attendance.infrastructure.persistence;

import java.time.Instant;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

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
@Entity
@Table(name = "attendance_records")
@IdClass(AttendanceRecordId.class)
public class AttendanceRecordJpaEntity implements Persistable<AttendanceRecordId> {

    @Id
    @Column(name = "session_id")
    private Long sessionId;

    @Id
    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt;

    protected AttendanceRecordJpaEntity() {
    }

    public AttendanceRecordJpaEntity(Long sessionId, Long participantId, Instant registeredAt) {
        this.sessionId = sessionId;
        this.participantId = participantId;
        this.registeredAt = registeredAt;
    }

    @Override
    public AttendanceRecordId getId() {
        return new AttendanceRecordId(sessionId, participantId);
    }

    /** Siempre nuevo: fuerza un INSERT, para que un duplicado choque con la PK y no haga un merge. */
    @Override
    public boolean isNew() {
        return true;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }
}
