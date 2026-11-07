/**
 * <h2>Propósito</h2>
 * Kernel compartido: piezas que todos los módulos pueden usar (error de negocio base, propiedades de configuración y reloj).
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: los módulos dependen de estas abstracciones mínimas en vez de depender entre sí.<br>
 * Debe mantenerse pequeño: si algo solo lo usa un módulo, va en ese módulo.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone: {@code shared.domain} y {@code shared.config} a todos los módulos (por eso es OPEN).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Ya está anotado como módulo OPEN: sus subpaquetes son visibles para todos.</li>
 *   <li>Nunca agregar aquí dependencias hacia otros módulos (shared no conoce a nadie).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada propio.</li>
 * </ul>
 */
@org.springframework.modulith.ApplicationModule(type = org.springframework.modulith.ApplicationModule.Type.OPEN)
package com.attendance.shared;
