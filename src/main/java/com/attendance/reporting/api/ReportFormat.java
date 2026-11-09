package com.attendance.reporting.api;

/**
 * <h2>Propósito</h2>
 * Formatos de exportación disponibles.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: agregar {@code PDF} o {@code XLSX} aquí y crear su {@code ReportExporter}; nada más cambia.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Valores: {@code CSV} (extensible).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Opcional: guardar en cada valor la extensión y el content type ({@code csv}, {@code text/csv}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public enum ReportFormat {
    CSV("csv", "text/csv");

    private final String extension;
    private final String contentType;

    ReportFormat(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String extension() {
        return extension;
    }

    public String contentType() {
        return contentType;
    }
}
