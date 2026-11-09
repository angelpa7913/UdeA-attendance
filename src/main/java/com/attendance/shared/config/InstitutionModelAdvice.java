package com.attendance.shared.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * <h2>Propósito</h2>
 * Pone {@code institutionName} en el modelo de todas las vistas de los controladores.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: evita repetir este atributo en cada controlador.
 *
 * <h2>Depende de</h2>
 * {@code AttendanceProperties} y Spring MVC.
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: lo sugiere la guía de {@code index.html}.
 */
@ControllerAdvice
public class InstitutionModelAdvice {

    private final AttendanceProperties properties;

    public InstitutionModelAdvice(AttendanceProperties properties) {
        this.properties = properties;
    }

    @ModelAttribute("institutionName")
    public String institutionName() {
        return properties.institutionName();
    }
}
