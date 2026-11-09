package com.attendance.enrollment.api;

import java.util.List;

/**
 * <h2>Propósito</h2>
 * Resumen de una importación: inscripciones nuevas, filas omitidas y errores legibles.
 *
 * <h2>Depende de</h2>
 * Nada.
 */
public record ImportSummary(int imported, int skipped, List<String> errors) {

    public ImportSummary {
        errors = List.copyOf(errors);
    }
}
