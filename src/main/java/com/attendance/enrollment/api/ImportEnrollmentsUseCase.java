package com.attendance.enrollment.api;

import java.io.InputStream;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: carga masiva de inscritos desde una fuente (CSV hoy, API externa mañana).
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo importar.<br>
 * O: el formato se elige por {@code sourceType}; agregar fuentes no cambia esta interfaz.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String sourceType} (ej. {@code "csv"}), {@code InputStream data}, correo del instructor que importa.</li>
 *   <li>Salida: un record de resumen (crea {@code ImportSummary(int imported, int skipped, List<String> errors)} en api).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear el record {@code ImportSummary} en este paquete.</li>
 *   <li>Declarar {@code ImportSummary importEnrollments(String sourceType, InputStream data, String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface ImportEnrollmentsUseCase {

    ImportSummary importEnrollments(String sourceType, InputStream data, String instructorEmail);
}
