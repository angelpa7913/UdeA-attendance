-- Propósito: datos de prueba para el perfil dev (H2). Migración repetible: Flyway la vuelve a
-- ejecutar si cambia su contenido. Nunca se carga en producción (solo está en las locations de dev).
--
-- Pasos a implementar:
--   1. Un curso: code 'DEMO-101', group_code '01', name 'Curso de prueba',
--      instructor_email = tu correo de prueba (debe pertenecer a un dominio de application-dev.yml).
--   2. Tres participantes con correos @example.com.
--   3. Inscribirlos en el curso.
--   4. Usar MERGE INTO o borrar antes de insertar para que sea repetible (MERGE es estándar SQL:2003).

MERGE INTO courses AS target
USING (VALUES ('DEMO-101', '01', 'Curso de prueba', 'instructor@example.com'))
    AS source (code, group_code, name, instructor_email)
ON target.code = source.code AND target.group_code = source.group_code
WHEN MATCHED THEN
    UPDATE SET name = source.name, instructor_email = source.instructor_email
WHEN NOT MATCHED THEN
    INSERT (code, group_code, name, instructor_email)
    VALUES (source.code, source.group_code, source.name, source.instructor_email);

MERGE INTO participants AS target
USING (VALUES
        ('ana@example.com', 'Ana Prueba', 'P-001'),
        ('bruno@example.com', 'Bruno Prueba', 'P-002'),
        ('carla@example.com', 'Carla Prueba', 'P-003'))
    AS source (email, full_name, external_id)
ON target.email = source.email
WHEN MATCHED THEN
    UPDATE SET full_name = source.full_name, external_id = source.external_id
WHEN NOT MATCHED THEN
    INSERT (email, full_name, external_id)
    VALUES (source.email, source.full_name, source.external_id);

MERGE INTO enrollments AS target
USING (
    SELECT p.id AS participant_id, c.id AS course_id
    FROM participants p
    CROSS JOIN courses c
    WHERE p.email IN ('ana@example.com', 'bruno@example.com', 'carla@example.com')
      AND c.code = 'DEMO-101'
      AND c.group_code = '01'
) AS source
ON target.participant_id = source.participant_id AND target.course_id = source.course_id
WHEN NOT MATCHED THEN
    INSERT (participant_id, course_id)
    VALUES (source.participant_id, source.course_id);
