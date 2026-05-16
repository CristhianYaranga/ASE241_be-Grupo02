
-- ============================================================
--  AgroGuard - Script Físico de Base de Datos
--  Motor: SQL Server
--  Grupo: 02
--  Módulos:
--    1. Tareas
--    2. Usuarios y Cultivos
--    3. Catálogo
--    4. Actividad
-- ============================================================

CREATE DATABASE AgroGuard;
GO

USE AgroGuard;
GO

-- ============================================================
--  MÓDULO 2: USUARIOS Y CULTIVOS
-- ============================================================

CREATE TABLE USERS (
    id_user          INT            NOT NULL  IDENTITY(1,1),
    name_user        VARCHAR(100)   NOT NULL,
    lastname_user    VARCHAR(100)   NOT NULL,
    email            VARCHAR(150)   NOT NULL,
    password         VARCHAR(255)   NOT NULL,
    rol              VARCHAR(20)    NOT NULL  DEFAULT 'usuario',
    -- Auditoría
    creation_date    DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date    DATETIME           NULL,
    restoration_d    DATETIME           NULL,
    CONSTRAINT PK_USERS       PRIMARY KEY (id_user),
    CONSTRAINT UQ_USERS_email UNIQUE      (email),
    CONSTRAINT CK_USERS_rol   CHECK       (rol IN ('usuario', 'admin'))
);
GO

CREATE TABLE CROPS (
    id_crops         INT            NOT NULL  IDENTITY(1,1),
    name             VARCHAR(100)   NOT NULL,
    plant_type       VARCHAR(100)   NOT NULL,
    location         VARCHAR(150)       NULL,
    description      VARCHAR(500)       NULL,
    start_date       DATE               NULL,
    state            VARCHAR(20)    NOT NULL  DEFAULT 'activo',
    -- Auditoría
    creation_date    DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date    DATETIME           NULL,
    restoration_d    DATETIME           NULL,
    -- FK
    USERS_id_use     INT            NOT NULL,
    CONSTRAINT PK_CROPS          PRIMARY KEY (id_crops),
    CONSTRAINT FK_CROPS_USERS    FOREIGN KEY (USERS_id_use)  REFERENCES USERS(id_user),
    CONSTRAINT CK_CROPS_state    CHECK       (state IN ('activo', 'pausado', 'cosechado'))
);
GO


-- ============================================================
--  MÓDULO 1: TAREAS
-- ============================================================

CREATE TABLE TASK (
    id_task          INT            NOT NULL  IDENTITY(1,1),
    task_title       VARCHAR(150)   NOT NULL,
    description      VARCHAR(400)       NULL,
    expiration_dat   DATE               NULL,
    priority         VARCHAR(10)    NOT NULL  DEFAULT 'media',
    status           VARCHAR(15)    NOT NULL  DEFAULT 'pendiente',
    category         VARCHAR(50)        NULL,
    -- Auditoría
    creation_date    DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date    DATETIME           NULL,
    restoration_dat  DATETIME           NULL,
    -- FK
    id_crops         INT                NULL,
    id_user          INT            NOT NULL,
    CONSTRAINT PK_TASK           PRIMARY KEY (id_task),
    CONSTRAINT FK_TASK_CROPS     FOREIGN KEY (id_crops)  REFERENCES CROPS(id_crops),
    CONSTRAINT FK_TASK_USERS     FOREIGN KEY (id_user)   REFERENCES USERS(id_user),
    CONSTRAINT CK_TASK_priority  CHECK (priority IN ('baja', 'media', 'alta')),
    CONSTRAINT CK_TASK_status    CHECK (status   IN ('pendiente', 'completada', 'cancelada'))
);
GO


-- ============================================================
--  MÓDULO 3: CATÁLOGO
-- ============================================================

CREATE TABLE PESTS (
    id_pests         INT            NOT NULL  IDENTITY(1,1),
    name_pests       VARCHAR(100)   NOT NULL,
    category         VARCHAR(50)    NOT NULL,
    description      VARCHAR(500)       NULL,
    symptoms         VARCHAR(500)       NULL,
    risk_level       VARCHAR(10)    NOT NULL  DEFAULT 'medio',
    image_url        VARCHAR(300)       NULL,
    -- Auditoría
    creation_date    DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date    DATETIME           NULL,
    restoration_date DATETIME           NULL,
    CONSTRAINT PK_PESTS           PRIMARY KEY (id_pests),
    CONSTRAINT CK_PESTS_risk      CHECK (risk_level IN ('bajo', 'medio', 'alto'))
);
GO

CREATE TABLE DISEASES (
    id_diseases      INT            NOT NULL  IDENTITY(1,1),
    name             VARCHAR(100)   NOT NULL,
    type_diseases    VARCHAR(50)    NOT NULL,
    description      VARCHAR(500)       NULL,
    symptoms         VARCHAR(500)       NULL,
    risk_level       VARCHAR(10)    NOT NULL  DEFAULT 'medio',
    imagen_url       VARCHAR(300)       NULL,
    -- Auditoría
    creation_date    DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date    DATETIME           NULL,
    restoration_da   DATETIME           NULL,
    CONSTRAINT PK_DISEASES        PRIMARY KEY (id_diseases),
    CONSTRAINT CK_DISEASES_risk   CHECK (risk_level IN ('bajo', 'medio', 'alto'))
);
GO

CREATE TABLE TREATMENTS (
    id_treatment       INT            NOT NULL  IDENTITY(1,1),
    name_treatment     VARCHAR(100)   NOT NULL,
    type_treatment     VARCHAR(50)    NOT NULL,
    description        VARCHAR(500)       NULL,
    active_ingredient  VARCHAR(150)       NULL,
    recommended_dos    VARCHAR(100)       NULL,
    application_mode   VARCHAR(200)       NULL,
    is_organic         BIT            NOT NULL  DEFAULT 0,
    -- Auditoría
    creation_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date        DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date      DATETIME           NULL,
    restoration_date   DATETIME           NULL,
    CONSTRAINT PK_TREATMENTS PRIMARY KEY (id_treatment)
);
GO

-- Tablas de apoyo (sin auditoría completa, solo creation_date)

CREATE TABLE PEST_TREATMENT (
    id_pest_treatment  INT            NOT NULL  IDENTITY(1,1),
    effectiveness      VARCHAR(10)        NULL,
    notes              VARCHAR(300)       NULL,
    creation_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    -- FK
    id_pests           INT            NOT NULL,
    id_treatment       INT            NOT NULL,
    CONSTRAINT PK_PEST_TREATMENT      PRIMARY KEY (id_pest_treatment),
    CONSTRAINT FK_PT_PESTS            FOREIGN KEY (id_pests)      REFERENCES PESTS(id_pests),
    CONSTRAINT FK_PT_TREATMENTS       FOREIGN KEY (id_treatment)  REFERENCES TREATMENTS(id_treatment),
    CONSTRAINT CK_PT_effectiveness    CHECK (effectiveness IN ('baja', 'media', 'alta'))
);
GO

CREATE TABLE DISEASE_TREATMENT (
    id_disease_treatment INT           NOT NULL  IDENTITY(1,1),
    effectiveness        VARCHAR(10)       NULL,
    notes                VARCHAR(300)      NULL,
    creation_date        DATETIME      NOT NULL  DEFAULT GETDATE(),
    -- FK
    id_treatment         INT           NOT NULL,
    id_diseases          INT           NOT NULL,
    CONSTRAINT PK_DISEASE_TREATMENT    PRIMARY KEY (id_disease_treatment),
    CONSTRAINT FK_DT_TREATMENTS        FOREIGN KEY (id_treatment) REFERENCES TREATMENTS(id_treatment),
    CONSTRAINT FK_DT_DISEASES          FOREIGN KEY (id_diseases)  REFERENCES DISEASES(id_diseases),
    CONSTRAINT CK_DT_effectiveness     CHECK (effectiveness IN ('baja', 'media', 'alta'))
);
GO


-- ============================================================
--  MÓDULO 4: ACTIVIDAD
-- ============================================================

CREATE TABLE CASES_DETECTED (
    id_cases_detected  INT            NOT NULL  IDENTITY(1,1),
    detection_date     DATE           NOT NULL  DEFAULT CAST(GETDATE() AS DATE),
    description        VARCHAR(500)       NULL,
    severity           VARCHAR(10)    NOT NULL  DEFAULT 'media',
    imagen_url         VARCHAR(300)       NULL,
    status             VARCHAR(20)    NOT NULL  DEFAULT 'en proceso',
    -- Auditoría
    creation_date      DATETIME       NOT NULL  DEFAULT GETDATE(),
    update_date        DATETIME       NOT NULL  DEFAULT GETDATE(),
    deletion_date      DATETIME           NULL,
    restoration_date   DATETIME           NULL,
    -- FK
    id_pests           INT                NULL,
    id_diseases        INT                NULL,
    id_user            INT            NOT NULL,
    id_crops           INT            NOT NULL,
    CONSTRAINT PK_CASES_DETECTED      PRIMARY KEY (id_cases_detected),
    CONSTRAINT FK_CD_PESTS            FOREIGN KEY (id_pests)    REFERENCES PESTS(id_pests),
    CONSTRAINT FK_CD_DISEASES         FOREIGN KEY (id_diseases) REFERENCES DISEASES(id_diseases),
    CONSTRAINT FK_CD_USERS            FOREIGN KEY (id_user)     REFERENCES USERS(id_user),
    CONSTRAINT FK_CD_CROPS            FOREIGN KEY (id_crops)    REFERENCES CROPS(id_crops),
    CONSTRAINT CK_CD_severity         CHECK (severity IN ('leve', 'media', 'grave')),
    CONSTRAINT CK_CD_status           CHECK (status   IN ('en proceso', 'resuelto', 'perdido')),
    CONSTRAINT CK_CD_tipo             CHECK (id_pests IS NOT NULL OR id_diseases IS NOT NULL)
);
GO

CREATE TABLE TREATMENT_RECORD (
    id_treatment_record  INT              NOT NULL  IDENTITY(1,1),
    application_date     DATE             NOT NULL  DEFAULT CAST(GETDATE() AS DATE),
    quantity_used        DECIMAL(10,2)        NULL,
    result               VARCHAR(15)          NULL,
    observations         VARCHAR(400)         NULL,
    -- Auditoría
    creation_date        DATETIME         NOT NULL  DEFAULT GETDATE(),
    update_date          DATETIME         NOT NULL  DEFAULT GETDATE(),
    deletion_date        DATETIME             NULL,
    restoration_date     DATETIME             NULL,
    -- FK
    id_treatment         INT              NOT NULL,
    id_cases_detected    INT              NOT NULL,
    id_user              INT              NOT NULL,
    CONSTRAINT PK_TREATMENT_RECORD     PRIMARY KEY (id_treatment_record),
    CONSTRAINT FK_TR_TREATMENTS        FOREIGN KEY (id_treatment)      REFERENCES TREATMENTS(id_treatment),
    CONSTRAINT FK_TR_CASES             FOREIGN KEY (id_cases_detected) REFERENCES CASES_DETECTED(id_cases_detected),
    CONSTRAINT FK_TR_USERS             FOREIGN KEY (id_user)           REFERENCES USERS(id_user),
    CONSTRAINT CK_TR_result            CHECK (result IN ('efectivo', 'parcial', 'sin efecto'))
);
GO


-- ============================================================
--  ÍNDICES DE RENDIMIENTO
-- ============================================================

CREATE INDEX IX_CROPS_usuario          ON CROPS(USERS_id_use);
CREATE INDEX IX_TASK_usuario           ON TASK(id_user);
CREATE INDEX IX_TASK_cultivo           ON TASK(id_crops);
CREATE INDEX IX_CD_usuario             ON CASES_DETECTED(id_user);
CREATE INDEX IX_CD_cultivo             ON CASES_DETECTED(id_crops);
CREATE INDEX IX_TR_caso                ON TREATMENT_RECORD(id_cases_detected);
CREATE INDEX IX_USERS_deletion         ON USERS(deletion_date);
CREATE INDEX IX_CROPS_deletion         ON CROPS(deletion_date);
CREATE INDEX IX_CASES_deletion         ON CASES_DETECTED(deletion_date);
GO


-- ============================================================
--  DATOS DE PRUEBA
-- ============================================================

-- Usuarios
INSERT INTO USERS (name_user, lastname_user, email, password, rol) VALUES
('Carlos',  'Quispe',   'carlos@agroguard.com',  'hash1234', 'admin'),
('Maria',   'Torres',   'maria@agroguard.com',   'hash5678', 'usuario'),
('Luis',    'Mamani',   'luis@agroguard.com',    'hash9012', 'usuario');

-- Cultivos
INSERT INTO CROPS (name, plant_type, location, description, start_date, state, USERS_id_use) VALUES
('Huerto principal',  'Tomate',   'Zona A',  'Cultivo de tomates cherry',  '2026-01-10', 'activo',    1),
('Invernadero 1',     'Pepino',   'Zona B',  'Pepinos para exportación',   '2026-02-15', 'activo',    2),
('Jardín trasero',    'Rosa',     'Zona C',  'Rosales ornamentales',       '2026-03-01', 'pausado',   3);

-- Plagas
INSERT INTO PESTS (name_pests, category, description, symptoms, risk_level) VALUES
('Pulgón',       'Insecto', 'Insecto que succiona la savia de las plantas.',  'Hojas enrolladas, pegajosidad.',       'medio'),
('Mosca Blanca', 'Insecto', 'Mosca que se agrupa en el envés de las hojas.', 'Manchas en hojas, debilitamiento.',    'alto'),
('Araña Roja',   'Ácaro',   'Ácaro que forma telarañas finas.',              'Puntos amarillos, telaraña en hojas.', 'medio');

-- Enfermedades
INSERT INTO DISEASES (name, type_diseases, description, symptoms, risk_level) VALUES
('Oidio',    'Hongo',   'Hongo que forma polvo blanco sobre hojas y tallos.', 'Polvo blanco, hojas deformes.',      'medio'),
('Mildiu',   'Hongo',   'Hongo que causa manchas amarillas y marrones.',      'Manchas amarillas, caída de hojas.', 'alto'),
('Botrytis', 'Hongo',   'Hongo gris que afecta frutos y flores.',             'Moho gris, tejidos blandos.',        'alto');

-- Tratamientos
INSERT INTO TREATMENTS (name_treatment, type_treatment, recommended_dos, application_mode, is_organic) VALUES
('Jabón potásico',      'Orgánico',   '2% en agua',        'Pulverizar en hojas afectadas.',  1),
('Azufre mojable',      'Químico',    '3 g/L de agua',     'Pulverizar cada 10 días.',        0),
('Control biológico',   'Biológico',  'Según fabricante',  'Liberar en cultivo afectado.',    1),
('Fungicida sistémico', 'Químico',    '1.5 mL/L de agua',  'Aplicar foliar o al suelo.',      0);
select * from TREATMENTS
-- Relaciones plaga - tratamiento
INSERT INTO PEST_TREATMENT (effectiveness, notes, id_pests, id_treatment) VALUES
('alta',  'Aplicar en la mañana.',        1, 1),
('media', 'Repetir cada 7 días.',         2, 1),
('alta',  'Usar con equipo de seguridad.',3, 2);

-- Relaciones enfermedad - tratamiento
INSERT INTO DISEASE_TREATMENT (effectiveness, notes, id_treatment, id_diseases) VALUES
('alta',  'Aplicar en clima seco.',       2, 1),
('alta',  'Mejorar ventilación.',         4, 2),
('media', 'Retirar partes afectadas.',    4, 3);

-- Tareas
INSERT INTO TASK (task_title, description, expiration_dat, priority, status, id_crops, id_user) VALUES
('Revisión semanal de cultivos', 'Inspeccionar signos de plagas.',    '2026-05-20', 'alta',  'pendiente',  1, 1),
('Segunda aplicación fungicida', 'Aplicar azufre mojable.',           '2026-05-22', 'media', 'pendiente',  2, 2),
('Inspección de nuevos brotes',  'Verificar crecimiento de brotes.',  '2026-05-25', 'baja',  'pendiente',  3, 3);

-- Casos detectados
INSERT INTO CASES_DETECTED (detection_date, description, severity, status, id_pests, id_diseases, id_user, id_crops) VALUES
('2026-05-10', 'Presencia de pulgones en hojas bajas.',    'media', 'en proceso', 1, NULL, 2, 1),
('2026-05-12', 'Manchas blancas en hojas superiores.',    'leve',  'en proceso', NULL, 1,  3, 2),
('2026-05-14', 'Decoloración amarilla en hojas.',         'grave', 'en proceso', NULL, 2,  1, 3);

-- Registros de tratamiento
INSERT INTO TREATMENT_RECORD (application_date, quantity_used, result, observations, id_treatment, id_cases_detected, id_user) VALUES
('2026-05-11', 0.50, 'parcial',  'Se redujo la presencia a la mitad.',   1, 1, 2),
('2026-05-13', 1.20, 'efectivo', 'Desapareció el polvo blanco.',         2, 2, 3),
('2026-05-15', 2.00, 'parcial',  'Mejoró pero requiere otra aplicación.',4, 3, 1);
GO
