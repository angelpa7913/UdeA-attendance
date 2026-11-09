package com.attendance.reporting.infrastructure.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.attendance.reporting.application.port.out.ReportDataSource;
import com.attendance.reporting.application.port.out.ReportRow;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ReportDataSource} leyendo la vista {@code attendance_report}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable. Solo lectura.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Las del puerto.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}. Constructor con {@code JdbcClient} (o {@code EntityManager} con consulta nativa).</li>
 *   <li>SQL estándar: {@code SELECT ... FROM attendance_report WHERE session_id = ? AND instructor_email = ? ORDER BY participant_name}.</li>
 *   <li>// SECURITY: parámetros enlazados, nunca concatenar valores en el SQL.</li>
 *   <li>Mapear cada fila a {@code ReportRow}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportDataSource}, {@code ReportRow} (port.out)</li>
 * </ul>
 */
@Component
public class JpaReportDataSource implements ReportDataSource {

    // SECURITY: parámetros enlazados; los valores nunca se concatenan en el SQL.
    private static final String ROWS_SQL = """
            SELECT course_code, course_group, course_name, session_id, session_opened_at,
                   participant_email, participant_name, external_id, registered_at
            FROM attendance_report
            WHERE session_id = ? AND instructor_email = ?
            ORDER BY participant_name
            """;

    private final JdbcClient jdbcClient;

    public JpaReportDataSource(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ReportRow> rowsFor(long sessionId, String instructorEmail) {
        return jdbcClient.sql(ROWS_SQL).params(sessionId, instructorEmail)
                .query((resultSet, rowNumber) -> toRow(resultSet))
                .list();
    }

    private static ReportRow toRow(ResultSet resultSet) throws SQLException {
        return new ReportRow(resultSet.getString("course_code"), resultSet.getString("course_group"),
                resultSet.getString("course_name"), resultSet.getLong("session_id"),
                instant(resultSet, "session_opened_at"), resultSet.getString("participant_email"),
                resultSet.getString("participant_name"), resultSet.getString("external_id"),
                instant(resultSet, "registered_at"));
    }

    private static Instant instant(ResultSet resultSet, String column) throws SQLException {
        return resultSet.getObject(column, OffsetDateTime.class).toInstant();
    }
}
