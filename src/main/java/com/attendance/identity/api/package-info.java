/**
 * <h2>Propósito</h2>
 * API pública del módulo {@code identity}: casos de uso (puertos de entrada) y DTOs.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: los demás módulos dependen solo de estas interfaces, nunca de la implementación.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone: interfaces {@code *UseCase} y records DTO.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Mantener solo interfaces y records inmutables aquí.</li>
 *   <li>No exponer entidades de dominio: convertirlas a DTO en la capa application.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared}</li>
 * </ul>
 */
@org.springframework.modulith.NamedInterface("api")
package com.attendance.identity.api;
