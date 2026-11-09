package com.attendance.enrollment.infrastructure.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.attendance.enrollment.application.port.out.EnrollmentRow;
import com.attendance.enrollment.application.port.out.EnrollmentSource;

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
@Component
public class CsvEnrollmentSource implements EnrollmentSource {

    private static final String SOURCE_TYPE = "csv";
    private static final String SEPARATOR = ",";
    private static final String BYTE_ORDER_MARK = "\uFEFF";
    private static final int KEEP_EMPTY_COLUMNS = -1;
    private static final String EMAIL = "email";
    private static final String FULL_NAME = "full_name";
    private static final String EXTERNAL_ID = "external_id";
    private static final String COURSE_CODE = "course_code";
    private static final String COURSE_GROUP = "course_group";
    private static final String COURSE_NAME = "course_name";
    private static final List<String> REQUIRED_COLUMNS =
            List.of(EMAIL, FULL_NAME, COURSE_CODE, COURSE_GROUP, COURSE_NAME);

    @Override
    public boolean supports(String sourceType) {
        return SOURCE_TYPE.equalsIgnoreCase(sourceType);
    }

    @Override
    public List<EnrollmentRow> read(InputStream data) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(data, StandardCharsets.UTF_8))) {
            final List<String> lines = reader.lines().filter(line -> !line.isBlank()).toList();
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("el archivo está vacío");
            }
            final List<String> headers = parseHeaders(lines.get(0));
            return lines.stream().skip(1).map(line -> toRow(headers, line)).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static List<String> parseHeaders(String line) {
        final List<String> headers = List.of(line.replace(BYTE_ORDER_MARK, "").split(SEPARATOR, KEEP_EMPTY_COLUMNS))
                .stream().map(header -> header.strip().toLowerCase(Locale.ROOT)).toList();
        final List<String> missing = REQUIRED_COLUMNS.stream().filter(column -> !headers.contains(column)).toList();
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("faltan las columnas " + String.join(", ", missing));
        }
        return headers;
    }

    private static EnrollmentRow toRow(List<String> headers, String line) {
        final String[] values = line.split(SEPARATOR, KEEP_EMPTY_COLUMNS);
        final Function<String, String> column = name -> valueAt(values, headers.indexOf(name));
        return new EnrollmentRow(column.apply(EMAIL), column.apply(FULL_NAME), column.apply(EXTERNAL_ID),
                column.apply(COURSE_CODE), column.apply(COURSE_GROUP), column.apply(COURSE_NAME));
    }

    private static String valueAt(String[] values, int index) {
        return index >= 0 && index < values.length ? values[index].strip() : "";
    }
}
