/**
 * <h2>Propósito</h2>
 * Módulo de identidad: inicio de sesión con cualquier proveedor OIDC, restricción por dominio de correo y usuario actual.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo responde "¿quién es el usuario?". No sabe de cursos, sesiones ni asistencia.<br>
 * O: otro proveedor de login = otro bloque en el YAML, sin tocar este código.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone (api): {@code GetCurrentUserUseCase}, {@code CurrentUser}, {@code InstructorDirectory}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Implementar primero {@code domain}, luego {@code application}, por último {@code infrastructure/security}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared} únicamente. No depende de ningún otro módulo.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * El rol INSTRUCTOR se decide con {@code InstructorDirectory}, que implementa el módulo enrollment.<br>
 * Así identity no depende de enrollment (evita un ciclo): inversión de dependencias entre módulos.<br>
 */
@org.springframework.modulith.ApplicationModule(displayName = "Identity")
package com.attendance.identity;
