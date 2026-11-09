package com.attendance.identity.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.attendance.identity.api.CurrentUser;
import com.attendance.identity.application.port.out.AuthenticatedUserProvider.AuthenticatedUser;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code GetCurrentUserService}.
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
 *   <li>{@code GetCurrentUserService}</li>
 *   <li>Fakes de {@code AuthenticatedUserProvider} e {@code InstructorDirectory}</li>
 * </ul>
 */
class GetCurrentUserServiceTest {

    private static final AuthenticatedUser USER =
            new AuthenticatedUser("sub-1", "ana@example.com", "Ana Prueba");

    @Test
    void returnsEmptyWhenNoAuthenticatedUser() {
        final GetCurrentUserService service = new GetCurrentUserService(Optional::empty, email -> true);

        assertThat(service.currentUser()).isEmpty();
    }

    @Test
    void assignsInstructorRoleWhenDirectorySaysSo() {
        final GetCurrentUserService service = new GetCurrentUserService(() -> Optional.of(USER), email -> true);

        assertThat(service.currentUser()).map(CurrentUser::role).contains("INSTRUCTOR");
    }

    @Test
    void assignsParticipantRoleByDefault() {
        final GetCurrentUserService service = new GetCurrentUserService(() -> Optional.of(USER), email -> false);

        assertThat(service.currentUser()).map(CurrentUser::role).contains("PARTICIPANT");
    }
}
