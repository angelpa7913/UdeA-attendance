-- Propósito: tabla del módulo attendance: attendance_records.
--
-- Pasos a implementar:
--   1. attendance_records:
--        session_id BIGINT NOT NULL REFERENCES sessions(id),
--        participant_id BIGINT NOT NULL REFERENCES participants(id),
--        registered_at TIMESTAMP WITH TIME ZONE NOT NULL,
--        PRIMARY KEY (session_id, participant_id)      -- defensa final contra duplicados
--
-- La PK compuesta hace que dos escaneos simultáneos del mismo participante no creen dos filas,
-- aunque ambos pasen la verificación exists() en la aplicación.

CREATE TABLE attendance_records (
    session_id BIGINT NOT NULL REFERENCES sessions(id),
    participant_id BIGINT NOT NULL REFERENCES participants(id),
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (session_id, participant_id)
);
