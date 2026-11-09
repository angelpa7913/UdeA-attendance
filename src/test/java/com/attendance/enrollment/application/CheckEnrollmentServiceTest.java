package com.attendance.enrollment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.attendance.enrollment.application.port.out.EnrollmentRepository;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code CheckEnrollmentService}.
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
 *   <li>{@code CheckEnrollmentService}</li>
 *   <li>{@code EnrollmentRepository} en memoria o mock</li>
 * </ul>
 */
class CheckEnrollmentServiceTest {

    private final EnrollmentRepository repository = mock(EnrollmentRepository.class);
    private final CheckEnrollmentService service = new CheckEnrollmentService(repository);

    @Test
    void returnsParticipantIdWhenEnrolled() {
        when(repository.findParticipantIdEnrolled("ana@x.com", 7L)).thenReturn(Optional.of(3L));

        assertThat(service.findParticipantId("ana@x.com", 7L)).contains(3L);
    }

    @Test
    void returnsEmptyWhenNotEnrolled() {
        when(repository.findParticipantIdEnrolled("ana@x.com", 7L)).thenReturn(Optional.empty());

        assertThat(service.findParticipantId("ana@x.com", 7L)).isEmpty();
    }

    @Test
    void ignoresEmailCase() {
        when(repository.findParticipantIdEnrolled("ana@x.com", 7L)).thenReturn(Optional.of(3L));

        assertThat(service.findParticipantId("Ana@X.COM", 7L)).contains(3L);
    }
}
