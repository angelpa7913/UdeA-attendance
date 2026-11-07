package com.attendance.attendance.infrastructure.web;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: el participante llega desde el QR y ve el resultado.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida, llama el caso de uso y responde.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code GET /attend?t=token} → vista {@code attend/result}.</li>
 *   <li>{@code GET /instructor/sessions/{id}/count} → JSON {@code {count}} para la proyección.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code RegisterAttendanceUseCase}, {@code CountAttendeesUseCase}.</li>
 *   <li>Si no hay usuario, Spring Security ya redirigió al login y volverá aquí con el mismo {@code t}.</li>
 *   <li>Llamar {@code register(t, user.email())} y traducir el {@code RegistrationResult} con {@code switch} a un mensaje.</li>
 *   <li>// SECURITY: no mostrar por qué falló un token más allá de "QR inválido o vencido".</li>
 *   <li>// SECURITY: evaluar si debe ser {@code POST} (con confirmación) para que un enlace reenviado no registre a otro sin querer; el token vence en segundos de todas formas.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>api de attendance</li>
 * </ul>
 */
public class AttendanceController {
}
