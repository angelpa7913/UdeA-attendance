package com.attendance.enrollment.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h2>Propósito</h2>
 * Repositorio Spring Data de {@code CourseJpaEntity}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: acceso a una tabla. Solo lo usa {@code EnrollmentPersistenceAdapter} (nunca un servicio ni un controlador).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Métodos sugeridos: {@code List<CourseJpaEntity> findByInstructorEmail(String email)}, {@code Optional<...> findByCodeAndGroupCode(...)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Extender {@code JpaRepository<CourseJpaEntity, ...>} con el tipo de id correcto.</li>
 *   <li>Preferir métodos derivados; si usas {@code @Query}, que sea JPQL (portable entre motores).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code CourseJpaEntity}</li>
 * </ul>
 */
public interface CourseSpringDataRepository extends JpaRepository<CourseJpaEntity, Long> {

    List<CourseJpaEntity> findByInstructorEmail(String email);

    Optional<CourseJpaEntity> findByCodeAndGroupCode(String code, String groupCode);
}
