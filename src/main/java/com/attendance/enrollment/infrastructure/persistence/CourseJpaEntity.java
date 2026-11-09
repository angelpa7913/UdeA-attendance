package com.attendance.enrollment.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code courses}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo objeto-relacional. Distinta de la entidad de dominio; se unen con {@code EnrollmentMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: {@code id}, {@code code}, {@code groupCode} ({@code group} es palabra reservada en SQL), {@code name}, {@code instructorEmail}; UNIQUE(code, group_code).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Entity} y {@code @Table(name = "courses")}.</li>
 *   <li>Constructor protegido sin argumentos (lo exige JPA) y getters; evitar setters públicos innecesarios.</li>
 *   <li>Los nombres de columna deben coincidir con la migración Flyway (snake_case).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence. Nada de otros módulos.</li>
 * </ul>
 */
@Entity
@Table(name = "courses")
public class CourseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "group_code", nullable = false, length = 50)
    private String groupCode;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "instructor_email", nullable = false, length = 320)
    private String instructorEmail;

    protected CourseJpaEntity() {
    }

    public CourseJpaEntity(Long id, String code, String groupCode, String name, String instructorEmail) {
        this.id = id;
        this.code = code;
        this.groupCode = groupCode;
        this.name = name;
        this.instructorEmail = instructorEmail;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getName() {
        return name;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }
}
