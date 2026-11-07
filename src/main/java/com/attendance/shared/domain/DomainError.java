package com.attendance.shared.domain;

/**
 * <h2>Propósito</h2>
 * Error de negocio base: un código estable (para programas y traducciones) y un mensaje legible.
 * Se usa dentro de resultados tipados ({@code sealed interface}), no se lanza como excepción.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo describe un error. No sabe de HTTP, de vistas ni de logs.
 * L: cualquier módulo puede devolverlo sin que el consumidor sepa de qué módulo viene.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: {@code code} (por ejemplo {@code "session.closed"}) y {@code message}.
 * Salida: los dos campos, inmutables.
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Convertirlo en {@code record DomainError(String code, String message)}.</li>
 *   <li>En el constructor compacto: rechazar {@code code} nulo o en blanco
 *       ({@code IllegalArgumentException}: es un error de programación, no de negocio).</li>
 *   <li>Opcional: método estático {@code of(code, message)} para legibilidad.</li>
 *   <li>Sin anotaciones de Spring ni JPA (lo verifica {@code ArchitectureTest}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * Nada (solo {@code java.lang}).
 */
public record DomainError() {
}
