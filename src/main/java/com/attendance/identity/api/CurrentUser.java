package com.attendance.identity.api;

/**
 * <h2>Propósito</h2>
 * DTO inmutable con los datos del usuario autenticado que otros módulos necesitan.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo transporta datos. No expone tipos de Spring Security ({@code OidcUser}) fuera del módulo.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes sugeridos: {@code String id} (claim {@code sub}), {@code String email}, {@code String displayName}, {@code Role role}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes al record.</li>
 *   <li>Constructor compacto: rechazar email o id en blanco.</li>
 *   <li>Exponer el rol como el enum {@code Role} del dominio o como {@code String}; decide y sé consistente (si usas el enum, muévelo o duplícalo en api, porque api no debe exponer domain).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada (o {@code Role} si decides moverlo a api).</li>
 * </ul>
 */
public record CurrentUser(String id, String email, String displayName, String role) {

    private static final String INSTRUCTOR_ROLE = "INSTRUCTOR";

    public CurrentUser {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id del usuario no puede estar en blanco");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo del usuario no puede estar en blanco");
        }
    }

    public boolean isInstructor() {
        return INSTRUCTOR_ROLE.equals(role);
    }
}
