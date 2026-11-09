package com.attendance.reporting.api;

/**
 * <h2>Propósito</h2>
 * Archivo de reporte listo para descargar.
 *
 * <h2>Depende de</h2>
 * Nada.
 */
public record GeneratedReport(String fileName, String contentType, byte[] content) {

    public GeneratedReport {
        content = content.clone();
    }

    @Override
    public byte[] content() {
        return content.clone();
    }
}
