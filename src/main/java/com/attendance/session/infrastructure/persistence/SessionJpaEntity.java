package com.attendance.session.infrastructure.persistence;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <h2>Propósito</h2>
 * Entidad JPA de la tabla {@code sessions}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo mapeo. Se convierte con {@code SessionMapper}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Campos: {@code id}, {@code courseId}, {@code instructorEmail}, {@code secret}, {@code openedAt}, {@code closedAt} (nullable).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Entity}, {@code @Table(name = "sessions")}.</li>
 *   <li>{@code courseId} es un número, no una relación JPA a {@code CourseJpaEntity} (cada módulo es dueño de sus tablas).</li>
 *   <li>Constructor protegido sin argumentos y getters.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Jakarta Persistence</li>
 * </ul>
 */
@Entity
@Table(name = "sessions")
public class SessionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private long courseId;

    @Column(name = "instructor_email", nullable = false, length = 320)
    private String instructorEmail;

    @Column(name = "secret", nullable = false, length = 100)
    private String secret;

    @Column(name = "opened_at", nullable = false)
    private Instant openedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    protected SessionJpaEntity() {
    }

    public SessionJpaEntity(Long id, long courseId, String instructorEmail, String secret, Instant openedAt,
            Instant closedAt) {
        this.id = id;
        this.courseId = courseId;
        this.instructorEmail = instructorEmail;
        this.secret = secret;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }

    public Long getId() {
        return id;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public String getSecret() {
        return secret;
    }

    public Instant getOpenedAt() {
        return openedAt;
    }

    public Instant getClosedAt() {
        return closedAt;
    }
}
