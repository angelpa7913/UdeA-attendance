package com.attendance.session.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.attendance.session.api.ValidatedSession;
import com.attendance.session.application.port.out.SessionRepository;
import com.attendance.session.application.port.out.TokenSigner;
import com.attendance.session.domain.ClassSession;
import com.attendance.session.domain.QrToken;
import com.attendance.session.infrastructure.crypto.HmacTokenSigner;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code ValidateTokenService} con {@code Clock.fixed} y un {@code TokenSigner} real o fake.
 *
 * <h2>Responsabilidad SOLID</h2>
 * Prueba unitaria: se ejecuta sin levantar Spring (Mockito o adaptadores en memoria).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: escenarios descritos en cada método.</li>
 *   <li>Salida: aserciones sobre el resultado.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear los dobles de prueba (mocks o fakes en memoria) de cada puerto.</li>
 *   <li>Usar {@code Clock.fixed(...)} cuando el tiempo importe.</li>
 *   <li>Implementar cada escenario y quitar {@code @Disabled}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ValidateTokenService}</li>
 *   <li>{@code SessionRepository} en memoria</li>
 *   <li>{@code HmacTokenSigner} o fake</li>
 * </ul>
 */
class ValidateTokenServiceTest {

    private static final long SESSION_ID = 1L;
    private static final long COURSE_ID = 9L;
    private static final String SECRET = "secreto-de-prueba";
    private static final Instant NOW = Instant.ofEpochSecond(1_000);
    private static final long CURRENT_WINDOW = 50L;

    private final TokenSigner signer = new HmacTokenSigner();
    private ClassSession stored;
    private ValidateTokenService service;

    @BeforeEach
    void setUp() {
        stored = session(Optional.empty());
        final AttendanceProperties properties = new AttendanceProperties("Institución", List.of("x.com"),
                new AttendanceProperties.Qr(20, 1));
        service = new ValidateTokenService(new SingleSessionRepository(), signer, properties,
                Clock.fixed(NOW, ZoneOffset.UTC));
    }

    @Test
    void acceptsTokenFromCurrentWindow() {
        assertThat(service.validate(tokenFor(CURRENT_WINDOW)))
                .contains(new ValidatedSession(SESSION_ID, COURSE_ID, true));
    }

    @Test
    void acceptsTokenWithinTolerance() {
        assertThat(service.validate(tokenFor(CURRENT_WINDOW - 1))).isPresent();
    }

    @Test
    void rejectsTokenOutsideTolerance() {
        assertThat(service.validate(tokenFor(CURRENT_WINDOW - 2))).isEmpty();
    }

    @Test
    void rejectsFutureWindow() {
        assertThat(service.validate(tokenFor(CURRENT_WINDOW + 1))).isEmpty();
    }

    @Test
    void rejectsTamperedSignature() {
        final String token = tokenFor(CURRENT_WINDOW);
        final char replacement = token.endsWith("A") ? 'B' : 'A';

        assertThat(service.validate(token.substring(0, token.length() - 1) + replacement)).isEmpty();
    }

    @Test
    void rejectsUnknownSession() {
        final String data = new QrToken(2L, CURRENT_WINDOW, "").signedData();
        final String token = new QrToken(2L, CURRENT_WINDOW, signer.sign(data, SECRET)).serialize();

        assertThat(service.validate(token)).isEmpty();
    }

    @Test
    void reportsClosedSession() {
        stored = session(Optional.of(NOW));

        assertThat(service.validate(tokenFor(CURRENT_WINDOW)))
                .contains(new ValidatedSession(SESSION_ID, COURSE_ID, false));
    }

    private String tokenFor(long window) {
        final String data = new QrToken(SESSION_ID, window, "").signedData();
        return new QrToken(SESSION_ID, window, signer.sign(data, SECRET)).serialize();
    }

    private static ClassSession session(Optional<Instant> closedAt) {
        return ClassSession.restore(SESSION_ID, COURSE_ID, "instructor@x.com", SECRET, NOW, closedAt);
    }

    private final class SingleSessionRepository implements SessionRepository {

        @Override
        public ClassSession save(ClassSession session) {
            return session;
        }

        @Override
        public Optional<ClassSession> findById(long id) {
            return id == SESSION_ID ? Optional.of(stored) : Optional.empty();
        }

        @Override
        public Optional<ClassSession> findOpenByCourse(long courseId) {
            return Optional.empty();
        }
    }
}
