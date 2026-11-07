package com.attendance.shared.config;

/**
 * <h2>Propósito</h2>
 * Única fuente de los valores configurables del negocio (prefijo {@code attendance} en
 * {@code application.yml}): nombre de la institución, dominios de correo permitidos,
 * duración de la ventana del QR y tolerancia.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo transporta configuración, no la interpreta.
 * O: cambiar de institución = cambiar el YAML, sin tocar código.
 * Elimina números y textos mágicos del resto del proyecto.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: propiedades {@code attendance.*} que Spring enlaza al arrancar.
 * Salida (sugerida):
 * <ul>
 *   <li>{@code String institutionName()}</li>
 *   <li>{@code List<String> allowedEmailDomains()}</li>
 *   <li>{@code Qr qr()} con {@code int windowSeconds()} y {@code int toleranceWindows()}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @ConfigurationProperties(prefix = "attendance")} y {@code @Validated}
 *       (ya se registra gracias a {@code @ConfigurationPropertiesScan}).</li>
 *   <li>Agregar los componentes: {@code @NotBlank institutionName},
 *       {@code @NotEmpty List<String> allowedEmailDomains}, {@code @Valid @NotNull Qr qr}.</li>
 *   <li>Crear un record anidado {@code Qr(@Positive int windowSeconds, @PositiveOrZero int toleranceWindows)}.</li>
 *   <li>Constructor compacto: copiar la lista con {@code List.copyOf} (inmutable) y normalizar
 *       los dominios a minúsculas.</li>
 *   <li>Si falta un valor, la app no debe arrancar (eso lo logra {@code @Validated}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * Spring Boot (enlace de propiedades) y Jakarta Validation. Ningún módulo.
 * Los módulos lo reciben por constructor; el {@code domain} de un módulo NO debe recibirlo
 * (pásale solo los valores que necesita).
 */
public record AttendanceProperties() {
}
