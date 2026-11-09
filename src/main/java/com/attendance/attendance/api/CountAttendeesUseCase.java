package com.attendance.attendance.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: cuántos asistentes lleva una sesión (contador en la pantalla de proyección).
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una consulta.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code long sessionId}.</li>
 *   <li>Salida: {@code long}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code long countBySession(long sessionId);}.</li>
 *   <li>Decidir cómo se protege: el controlador debe comprobar que quien pregunta es el instructor (puedes reutilizar {@code GetCurrentTokenUseCase} como verificación de propiedad, o agregar un caso de uso en session).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface CountAttendeesUseCase {

    long countBySession(long sessionId);
}
