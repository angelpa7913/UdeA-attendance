package com.attendance.enrollment.application.port.out;

import java.io.InputStream;
import java.util.List;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: leer filas de inscritos desde cualquier fuente (CSV, Excel, API de la institución).
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: cada formato es una implementación nueva; {@code ImportEnrollmentsService} no cambia.<br>
 * I: dos métodos.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code boolean supports(String sourceType)}</li>
 *   <li>{@code List<EnrollmentRow> read(InputStream data)}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar ambos métodos.</li>
 *   <li>Definir qué pasa con filas inválidas: la implementación las reporta (no las descarta en silencio).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentRow}</li>
 * </ul>
 */
public interface EnrollmentSource {

    boolean supports(String sourceType);

    /**
     * Lee las filas de la fuente. Una fila incompleta se devuelve con sus campos en blanco para que
     * el servicio la reporte; nunca se descarta en silencio.
     *
     * @throws IllegalArgumentException si la estructura de la fuente no es válida
     * @throws java.io.UncheckedIOException si no se pudo leer
     */
    List<EnrollmentRow> read(InputStream data);
}
