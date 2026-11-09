package com.attendance.identity.infrastructure.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import com.attendance.identity.domain.EmailAddress;
import com.attendance.identity.domain.EmailDomainPolicy;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Carga el usuario OIDC y rechaza el login si el dominio del correo no está permitido.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo filtra por dominio; la regla está en {@code EmailDomainPolicy}.<br>
 * L: es un {@code OidcUserService} sustituible por el de Spring.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code OidcUserRequest}.</li>
 *   <li>Salida: {@code OidcUser} o {@code OAuth2AuthenticationException} (es la forma que Spring espera para rechazar).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar con {@code @Component}; delegar en un {@code OidcUserService} interno (composición, no herencia).</li>
 *   <li>Constructor con {@code AttendanceProperties}; crear la {@code EmailDomainPolicy} con {@code allowedEmailDomains()}.</li>
 *   <li>{@code loadUser}: delegar, leer el claim {@code email} y crear {@code EmailAddress}.</li>
 *   <li>// SECURITY: exigir {@code email_verified == true} si el proveedor lo envía.</li>
 *   <li>// SECURITY: si el dominio no está permitido, lanzar {@code OAuth2AuthenticationException} con un código claro.</li>
 *   <li>Devolver el {@code OidcUser} original.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EmailAddress}, {@code EmailDomainPolicy} (domain)</li>
 *   <li>{@code AttendanceProperties} (shared)</li>
 * </ul>
 */
@Component
public class DomainRestrictedOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private static final String INVALID_EMAIL = "invalid_email";
    private static final String EMAIL_NOT_VERIFIED = "email_not_verified";
    private static final String EMAIL_DOMAIN_NOT_ALLOWED = "email_domain_not_allowed";

    private final OidcUserService delegate = new OidcUserService();
    private final EmailDomainPolicy emailDomainPolicy;

    public DomainRestrictedOidcUserService(AttendanceProperties properties) {
        this.emailDomainPolicy = new EmailDomainPolicy(properties.allowedEmailDomains());
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        final OidcUser user = delegate.loadUser(userRequest);
        final EmailAddress email = EmailAddress.parse(user.getEmail())
                .orElseThrow(() -> rejection(INVALID_EMAIL));
        // SECURITY: un correo que el proveedor marca como no verificado no prueba pertenencia al dominio.
        if (Boolean.FALSE.equals(user.getEmailVerified())) {
            throw rejection(EMAIL_NOT_VERIFIED);
        }
        // SECURITY: solo entran correos de los dominios configurados.
        if (!emailDomainPolicy.isAllowed(email)) {
            throw rejection(EMAIL_DOMAIN_NOT_ALLOWED);
        }
        return user;
    }

    private static OAuth2AuthenticationException rejection(String errorCode) {
        return new OAuth2AuthenticationException(new OAuth2Error(errorCode), errorCode);
    }
}
