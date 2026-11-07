package com.attendance.enrollment.infrastructure.csv;

/**
 * <h2>Propósito</h2>
 * Implementa {@code EnrollmentSource} para archivos CSV.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: un formato = una clase. S: solo parsea CSV.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code supports("csv")} → true.</li>
 *   <li>{@code read(InputStream)} → {@code List<EnrollmentRow>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements EnrollmentSource}.</li>
 *   <li>Leer con {@code BufferedReader} y {@code lines()} (stream finito; sin {@code while (true)}).</li>
 *   <li>Primera línea = encabezados; ubicar columnas por nombre, no por posición fija.</li>
 *   <li>Separador y nombres de columnas como constantes con nombre (o en configuración).</li>
 *   <li>Cerrar el lector con try-with-resources; capturar {@code IOException} específica, nunca {@code Exception}.</li>
 *   <li>// SECURITY: limitar el tamaño del archivo (spring.servlet.multipart.max-file-size).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentSource}, {@code EnrollmentRow} (port.out)</li>
 * </ul>
 */
public class CsvEnrollmentSource {
}
