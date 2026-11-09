package com.attendance.enrollment.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code participants}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo objeto-relacional. Distinta de la entidad de dominio; se unen con {@code EnrollmentMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: {@code id} (identity), {@code email} (único), {@code fullName}, {@code externalId}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Entity} y {@code @Table(name = "participants")}.</li>
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
@Table(name = "participants")
public class ParticipantJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "external_id", length = 100)
    private String externalId;

    protected ParticipantJpaEntity() {
    }

    public ParticipantJpaEntity(Long id, String email, String fullName, String externalId) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getExternalId() {
        return externalId;
    }
}
