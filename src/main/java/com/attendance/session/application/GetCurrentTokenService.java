package com.attendance.session.application;

import java.time.Clock;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.attendance.session.api.CurrentToken;
import com.attendance.session.api.GetCurrentTokenUseCase;
import com.attendance.session.application.port.out.SessionRepository;
import com.attendance.session.application.port.out.TokenSigner;
import com.attendance.session.domain.ClassSession;
import com.attendance.session.domain.QrToken;
import com.attendance.session.domain.TimeWindow;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Implementa {@code GetCurrentTokenUseCase}: calcula el token de la ventana actual.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso. Sin hilos ni bucles: cada petición calcula el token del momento.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sessionId, instructorEmail.</li>
 *   <li>Salida: {@code Optional<CurrentToken>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code SessionRepository}, {@code TokenSigner}, {@code AttendanceProperties}, {@code Clock}.</li>
 *   <li>Buscar la sesión; si no existe, está cerrada o no es del instructor → vacío.</li>
 *   <li>{@code window = TimeWindow.of(clock.instant(), windowSeconds)}.</li>
 *   <li>{@code signature = signer.sign(sessionId + "." + window, secret)}; armar {@code QrToken} y serializar.</li>
 *   <li>{@code expiresAt = window.end(windowSeconds)} para la cuenta regresiva del navegador.</li>
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
public class GetCurrentTokenService implements GetCurrentTokenUseCase {

    private final SessionRepository sessionRepository;
    private final TokenSigner tokenSigner;
    private final AttendanceProperties properties;
    private final Clock clock;

    public GetCurrentTokenService(SessionRepository sessionRepository, TokenSigner tokenSigner,
            AttendanceProperties properties, Clock clock) {
        this.sessionRepository = sessionRepository;
        this.tokenSigner = tokenSigner;
        this.properties = properties;
        this.clock = clock;
    }

    @Override
    public Optional<CurrentToken> currentToken(long sessionId, String instructorEmail) {
        return sessionRepository.findById(sessionId)
                .filter(ClassSession::isOpen)
                .filter(session -> session.isOwnedBy(instructorEmail))
                .map(session -> tokenFor(sessionId, session));
    }

    private CurrentToken tokenFor(long sessionId, ClassSession session) {
        final long windowSeconds = properties.qr().windowSeconds();
        final TimeWindow window = TimeWindow.of(clock.instant(), windowSeconds);
        final String data = new QrToken(sessionId, window.number(), "").signedData();
        final String signature = tokenSigner.sign(data, session.secret());
        final QrToken token = new QrToken(sessionId, window.number(), signature);
        return new CurrentToken(token.serialize(), window.end(windowSeconds));
    }
}
