package com.attendance.enrollment.domain;

/**
 * <h2>Propósito</h2>
 * Objeto de valor: el participante X está inscrito en el curso Y.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo la relación. Inmutable.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes sugeridos: {@code long participantId}, {@code long courseId}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes.</li>
 *   <li>Constructor compacto: ids positivos.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public record Enrollment(long participantId, long courseId) {

    public Enrollment {
        if (participantId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException("Los ids de la inscripción deben ser positivos");
        }
    }
}
