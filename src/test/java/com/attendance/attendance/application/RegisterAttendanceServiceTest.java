package com.attendance.attendance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.attendance.attendance.api.RegistrationResult;
import com.attendance.attendance.application.port.out.AttendanceRepository;
import com.attendance.attendance.domain.AttendanceRecord;
import com.attendance.enrollment.api.CheckEnrollmentUseCase;
import com.attendance.session.api.ValidateTokenUseCase;
import com.attendance.session.api.ValidatedSession;

/**
 * <h2>Propósito</h2>
 * Un escenario por cada caso de {@code RegistrationResult}.
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
 *   <li>{@code RegisterAttendanceService}</li>
 *   <li>Mocks de {@code ValidateTokenUseCase} y {@code CheckEnrollmentUseCase}</li>
 *   <li>{@code AttendanceRepository} en memoria</li>
 *   <li>{@code Clock.fixed}</li>
 * </ul>
 */
class RegisterAttendanceServiceTest {

    private static final String TOKEN = "1.2.firma";
    private static final String EMAIL = "ana@x.com";
    private static final long SESSION_ID = 1L;
    private static final long COURSE_ID = 9L;
    private static final long PARTICIPANT_ID = 3L;
    private static final Instant NOW = Instant.ofEpochSecond(1_000);

    private final ValidateTokenUseCase validateToken = mock(ValidateTokenUseCase.class);
    private final CheckEnrollmentUseCase checkEnrollment = mock(CheckEnrollmentUseCase.class);
    private final AttendanceRepository repository = mock(AttendanceRepository.class);
    private final RegisterAttendanceService service = new RegisterAttendanceService(validateToken, checkEnrollment,
            repository, Clock.fixed(NOW, ZoneOffset.UTC));

    @Test
    void returnsInvalidTokenWhenValidationFails() {
        when(validateToken.validate(TOKEN)).thenReturn(Optional.empty());

        assertThat(service.register(TOKEN, EMAIL)).isInstanceOf(RegistrationResult.InvalidToken.class);
        verifyNoInteractions(checkEnrollment);
    }

    @Test
    void returnsSessionClosedWhenSessionIsClosed() {
        session(false);

        assertThat(service.register(TOKEN, EMAIL)).isInstanceOf(RegistrationResult.SessionClosed.class);
    }

    @Test
    void returnsNotEnrolledWhenParticipantIsNotInCourse() {
        session(true);
        when(checkEnrollment.findParticipantId(EMAIL, COURSE_ID)).thenReturn(Optional.empty());

        assertThat(service.register(TOKEN, EMAIL)).isInstanceOf(RegistrationResult.NotEnrolled.class);
        verify(repository, never()).save(any());
    }

    @Test
    void returnsAlreadyRegisteredWhenRecordExists() {
        enrolled();
        when(repository.exists(SESSION_ID, PARTICIPANT_ID)).thenReturn(true);

        assertThat(service.register(TOKEN, EMAIL)).isInstanceOf(RegistrationResult.AlreadyRegistered.class);
        verify(repository, never()).save(any());
    }

    @Test
    void returnsAlreadyRegisteredOnConcurrentInsert() {
        enrolled();
        when(repository.save(any())).thenReturn(false);

        assertThat(service.register(TOKEN, EMAIL)).isInstanceOf(RegistrationResult.AlreadyRegistered.class);
    }

    @Test
    void returnsRegisteredAndUsesClockTime() {
        enrolled();
        when(repository.save(any())).thenReturn(true);

        assertThat(service.register(TOKEN, EMAIL)).isEqualTo(new RegistrationResult.Registered(SESSION_ID, NOW));
        verify(repository).save(new AttendanceRecord(SESSION_ID, PARTICIPANT_ID, NOW));
    }

    private void session(boolean open) {
        when(validateToken.validate(TOKEN)).thenReturn(Optional.of(new ValidatedSession(SESSION_ID, COURSE_ID, open)));
    }

    private void enrolled() {
        session(true);
        when(checkEnrollment.findParticipantId(EMAIL, COURSE_ID)).thenReturn(Optional.of(PARTICIPANT_ID));
    }
}
