# Attendance — registro de asistencia con QR rotativo

El instructor proyecta un QR que cambia cada N segundos. El participante lo escanea, inicia sesión con su cuenta institucional (cualquier proveedor OIDC) y su asistencia queda registrada. Al final, el instructor cierra la sesión y descarga el reporte.

Nació para la Universidad de Antioquia, pero **nada del código depende de una institución, un proveedor de login o un motor de base de datos**: todo eso va en configuración.

> Estado: esqueleto. Cada archivo trae un Javadoc-guía (propósito, principio SOLID, entradas/salidas, pasos y dependencias) con la lógica por implementar.

## Requisitos

- JDK 21 (un JRE no basta: hace falta `javac`)
- Maven 3.9+
- PostgreSQL (producción) — en desarrollo se usa H2 en memoria

Si tienes varias versiones de Java, apunta Maven al JDK 21:

```bash
export JAVA_HOME=/ruta/al/jdk-21
mvn -v   # debe decir "Java version: 21"
```

## Ejecutar

```bash
cp .env.example .env          # completa los valores
mvn verify                    # compila, Checkstyle y pruebas
mvn spring-boot:run -Dspring-boot.run.profiles=dev   # H2 + datos de prueba
```

## Configuración

Todo lo específico de una institución está en `src/main/resources/application.yml`:

| Propiedad | Para qué |
|-----------|----------|
| `attendance.institution-name` | Nombre que se muestra en las vistas |
| `attendance.allowed-email-domains` | Dominios de correo que pueden iniciar sesión |
| `attendance.qr.window-seconds` | Cada cuántos segundos cambia el QR |
| `attendance.qr.tolerance-windows` | Ventanas anteriores aceptadas (latencia al escanear) |

Variables de entorno (ver `.env.example`): `DB_URL`, `DB_USER`, `DB_PASSWORD`, `OIDC_CLIENT_ID`, `OIDC_CLIENT_SECRET`, `OIDC_ISSUER_URI`.

**Cambiar de proveedor de login** solo requiere cambiar `OIDC_ISSUER_URI` y las credenciales:

| Proveedor | `OIDC_ISSUER_URI` |
|-----------|-------------------|
| Google | `https://accounts.google.com` |
| Microsoft Entra ID | `https://login.microsoftonline.com/<tenant>/v2.0` |
| Keycloak | `https://<host>/realms/<realm>` |

La URL de redirección que debes registrar en el proveedor es `{baseUrl}/login/oauth2/code/oidc`.

## Arquitectura

Monolito modular (un solo `.jar`) con arquitectura hexagonal dentro de cada módulo. Spring Modulith (`ModularityTest`) y ArchUnit (`ArchitectureTest`) verifican los límites en cada build.

```
com.attendance
├── shared       kernel abierto: DomainError, AttendanceProperties, Clock
├── identity     login OIDC, restricción por dominio, usuario actual
├── enrollment   participantes, cursos, inscripciones, importación CSV
├── session      abrir/cerrar sesión, token QR firmado (HMAC-SHA256)
├── attendance   registrar asistencia, contar asistentes
└── reporting    exportar reportes (CSV hoy, PDF/Excel mañana)
```

Dependencias entre módulos (siempre a través del paquete `api`):

```
attendance ──► session ──► enrollment ──► identity
     │                         ▲              ▲
     └─────────────────────────┘              │
reporting ────────────────────────────────────┘
```

`identity` no conoce a `enrollment`: define la interfaz `InstructorDirectory` en su `api` y `enrollment` la implementa.

### Capas de cada módulo

| Paquete | Contiene | Puede depender de |
|---------|----------|-------------------|
| `api/` | Casos de uso (interfaces) y DTOs (records) | `shared` |
| `domain/` | Entidades y reglas puras, sin Spring ni JPA | `shared.domain` |
| `application/` | Servicios que implementan los casos de uso; `port/out/` | `api`, `domain`, `shared`, `api` de otros módulos |
| `infrastructure/` | Adaptadores: `web/`, `persistence/`, `security/`, ... | Todo lo anterior del mismo módulo |

### Token del QR

```
token     = sessionId.window.signature
window    = epochSeconds(clock) / windowSeconds
signature = Base64Url(HMAC-SHA256(sessionSecret, sessionId + "." + window))
```

Es válido si `window` es la actual o está hasta `toleranceWindows` atrás. La firma se compara en tiempo constante. El secreto nunca sale del servidor. La rotación no usa hilos: el servidor calcula el token del momento y el navegador recarga la imagen con `setInterval`.

### Base de datos

Cada módulo es dueño de sus tablas; ninguno escribe en las de otro. Migraciones Flyway en SQL estándar (corren en PostgreSQL y H2).

| Módulo | Tablas |
|--------|--------|
| enrollment | `participants`, `courses`, `enrollments` |
| session | `sessions` |
| attendance | `attendance_records` (PK `(session_id, participant_id)`) |
| reporting | solo lectura de la vista `attendance_report` |

## Cómo agregar un módulo

1. Crea `com.attendance.<modulo>` con `package-info.java` anotado con `@ApplicationModule`.
2. Crea las capas `api/` (con `package-info.java` anotado con `@NamedInterface("api")`), `domain/`, `application/port/out/` e `infrastructure/`.
3. Usa otros módulos solo a través de su `api`. Si un módulo existente necesita algo del nuevo, define la interfaz en el `api` del existente y que el nuevo la implemente (como `InstructorDirectory`).
4. Agrega sus tablas con una nueva migración `V<n>__<modulo>.sql`.
5. Corre `mvn verify`: `ModularityTest` falla si rompiste algún límite.

Extensiones típicas sin tocar código existente:
- **Nuevo formato de reporte:** valor en `ReportFormat` + clase `implements ReportExporter`.
- **Nueva fuente de inscritos:** clase `implements EnrollmentSource`.
- **Otro proveedor de login:** solo YAML.

## Orden sugerido de implementación

Regla general: `shared` → `domain` → `application` → `infrastructure` → `web`, escribiendo la prueba de cada servicio junto con él.

1. **shared**: `DomainError`, `AttendanceProperties`, `ClockConfig`. Verifica que la app arranca con el perfil dev.
2. **Migraciones** `V1`–`V4` y `R__dev_seed.sql`. Arranca en dev y revisa las tablas en la consola de H2 (`/h2-console`).
3. **identity**
   1. `EmailAddress`, `EmailDomainPolicy`, `Role`
   2. `AuthenticatedUserProvider`, `GetCurrentUserService` + `GetCurrentUserServiceTest`
   3. `SecurityConfig`, `DomainRestrictedOidcUserService`, `OidcAuthenticatedUserProvider` → prueba el login real
4. **enrollment**
   1. `Participant`, `Course`, `Enrollment`
   2. Puertos, `CheckEnrollmentService` + prueba, `ListInstructorCoursesService`
   3. Entidades JPA, mapper, repositorios, `EnrollmentPersistenceAdapter`
   4. `EnrollmentInstructorDirectory` (ya puedes entrar como instructor)
   5. `CsvEnrollmentSource`, `ImportEnrollmentsService`, `CourseController`, `instructor/courses.html`
5. **session**
   1. `TimeWindow`, `QrToken` + `QrTokenTest`
   2. `ClassSession`, puertos, `HmacTokenSigner`, `SecureRandomSecretGenerator`
   3. `StartSessionService`, `GetCurrentTokenService`, `ValidateTokenService` + `ValidateTokenServiceTest`, `CloseSessionService`
   4. Persistencia, `ZxingQrImageRenderer`, `SessionController`, `instructor/session.html`, `qr-rotator.js`
6. **attendance**
   1. `RegistrationResult`, `AttendanceRecord`
   2. `RegisterAttendanceService` + `RegisterAttendanceServiceTest` (un caso por resultado)
   3. Persistencia, `AttendanceController`, `attend/result.html` → prueba el flujo completo con el celular
7. **reporting**: `ReportRow`, `CsvReportExporter`, `GenerateReportService` + prueba, `JpaReportDataSource`, `ReportController`, `instructor/report.html`
8. Quita `@Disabled` de `ModularityTest` y `ArchitectureTest` y corrige lo que reporten.

## Reglas del código

Checkstyle hace fallar el build si se incumplen: sin números mágicos, métodos de ≤ 25 líneas, sin `if` anidados profundos, sin imports con `*`, sin `catch` vacíos ni `catch (Exception)`, sin `while (true)` / `for (;;)`, sin `X.now()` (usa el `Clock` inyectado) y sin `@Autowired` (inyección por constructor).

Además: DTOs y objetos de valor como `record`; nunca devolver `null` (usa `Optional`); resultados de negocio con `sealed interface`; código en inglés y documentación en español; los puntos críticos de seguridad van marcados con `// SECURITY:`.
