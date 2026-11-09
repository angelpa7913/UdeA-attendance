package com.attendance.session.application;

import java.time.Clock;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.attendance.session.api.ValidatedSession;
import com.attendance.session.api.ValidateTokenUseCase;
import com.attendance.session.application.port.out.SessionRepository;
import com.attendance.session.application.port.out.TokenSigner;
import com.attendance.session.domain.ClassSession;
import com.attendance.session.domain.QrToken;
import com.attendance.session.domain.TimeWindow;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ValidateTokenUseCase}: formato → sesión existe → firma → ventana.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso. Métodos cortos con retornos tempranos.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: token crudo.</li>
 *   <li>Salida: {@code Optional<ValidatedSession>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code SessionRepository}, {@code TokenSigner}, {@code AttendanceProperties}, {@code Clock}.</li>
 *   <li>{@code QrToken.parse(raw)}; vacío → vacío.</li>
 *   <li>Buscar la sesión por {@code token.sessionId()}; no existe → vacío.</li>
 *   <li>// SECURITY: {@code signer.verify(token.signedData(), token.signature(), session.secret())}; falso → vacío.</li>
 *   <li>Ventana actual con el {@code Clock}; si {@code token.window} no está dentro de {@code toleranceWindows} → vacío.</li>
 *   <li>Devolver {@code ValidatedSession(id, courseId, session.isOpen())}.</li>
 *   <li>Verificar la firma ANTES de mirar la ventana o el estado (no revelar información a quien no tiene firma válida).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionRepository}, {@code TokenSigner} (port.out)</li>
 *   <li>{@code AttendanceProperties}</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
@Service
public class ValidateTokenService implements ValidateTokenUseCase {

    private final SessionRepository sessionRepository;
    private final TokenSigner tokenSigner;
    private final AttendanceProperties properties;
    private final Clock clock;

    public ValidateTokenService(SessionRepository sessionRepository, TokenSigner tokenSigner,
            AttendanceProperties properties, Clock clock) {
        this.sessionRepository = sessionRepository;
        this.tokenSigner = tokenSigner;
        this.properties = properties;
        this.clock = clock;
    }

    @Override
    public Optional<ValidatedSession> validate(String rawToken) {
        return QrToken.parse(rawToken).flatMap(this::validate);
    }

    private Optional<ValidatedSession> validate(QrToken token) {
        return sessionRepository.findById(token.sessionId())
                // SECURITY: la firma va antes que la ventana y el estado, para no revelar nada sin firma válida.
                .filter(session -> tokenSigner.verify(token.signedData(), token.signature(), session.secret()))
                .filter(session -> isWithinTolerance(token))
                .map(session -> toValidated(token, session));
    }

    private boolean isWithinTolerance(QrToken token) {
        final TimeWindow current = TimeWindow.of(clock.instant(), properties.qr().windowSeconds());
        return new TimeWindow(token.window()).isWithinTolerance(current, properties.qr().toleranceWindows());
    }

    private static ValidatedSession toValidated(QrToken token, ClassSession session) {
        return new ValidatedSession(token.sessionId(), session.courseId(), session.isOpen());
    }
}
