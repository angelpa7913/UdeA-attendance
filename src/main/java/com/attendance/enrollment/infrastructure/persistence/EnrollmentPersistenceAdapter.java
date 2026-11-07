package com.attendance.enrollment.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Implementa {@code EnrollmentRepository} con Spring Data.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador del puerto; intercambiable por uno en memoria.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada/salida: las del puerto, siempre en tipos de dominio.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}, {@code implements EnrollmentRepository}.</li>
 *   <li>Constructor con los tres repositorios Spring Data.</li>
 *   <li>Cada método: llamar al repositorio y convertir con {@code EnrollmentMapper}.</li>
 *   <li>{@code enroll}: comprobar existencia antes de insertar (la PK es la defensa final).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentRepository} (port.out)</li>
 *   <li>Repositorios Spring Data y {@code EnrollmentMapper}</li>
 * </ul>
 */
public class EnrollmentPersistenceAdapter {
}
