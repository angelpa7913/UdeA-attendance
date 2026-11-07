package com.attendance.identity.domain;

/**
 * <h2>Propósito</h2>
 * Objeto de valor: un correo con formato válido, normalizado en minúsculas, del que se extrae el dominio.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo valida y representa un correo.<br>
 * Sin Spring ni JPA (regla pura).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String raw}.</li>
 *   <li>Salida: {@code String value()}, {@code String domain()}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Componente {@code String value}.</li>
 *   <li>Constructor compacto: recortar espacios, pasar a minúsculas, validar que haya exactamente una {@code @} con texto a ambos lados; si no, {@code IllegalArgumentException}.</li>
 *   <li>Método {@code domain()}: lo que sigue a la {@code @}.</li>
 *   <li>Opcional: {@code static Optional<EmailAddress> parse(String raw)} para no usar excepciones en el flujo normal.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public record EmailAddress() {
}
