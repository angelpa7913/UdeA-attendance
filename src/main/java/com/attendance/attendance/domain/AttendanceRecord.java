package com.attendance.attendance.domain;

import java.time.Instant;

/**
 * <h2>Propósito</h2>
 * Objeto de dominio: el participante X asistió a la sesión Y en el instante Z.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: inmutable, sin Spring ni JPA.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes: {@code long sessionId}, {@code long participantId}, {@code Instant registeredAt}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes.</li>
 *   <li>Constructor compacto: ids positivos y {@code registeredAt} no nulo.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada (solo {@code java.time}).</li>
 * </ul>
 */
public record AttendanceRecord(long sessionId, long participantId, Instant registeredAt) {

    public AttendanceRecord {
        if (sessionId <= 0 || participantId <= 0) {
            throw new IllegalArgumentException("Los ids del registro deben ser positivos");
        }
        if (registeredAt == null) {
            throw new IllegalArgumentException("La hora del registro es obligatoria");
        }
    }
}
