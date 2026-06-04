-- ─────────────────────────────────────────────────────────────────────────────
-- EHPAD Agir — Schéma initial (PostgreSQL)
-- V1 : Toutes les tables du projet
-- ─────────────────────────────────────────────────────────────────────────────

-- ── Utilisateurs ─────────────────────────────────────────────────────────────
CREATE TABLE app_users (
    id          BIGSERIAL       PRIMARY KEY,
    username    VARCHAR(100)    UNIQUE NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    email       VARCHAR(255)    UNIQUE,
    nom         VARCHAR(100),
    prenom      VARCHAR(100),
    role        VARCHAR(50)     DEFAULT 'USER',
    actif       BOOLEAN         DEFAULT TRUE,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

-- ── Aides-soignants ───────────────────────────────────────────────────────────
CREATE TABLE aides_soignants (
    id          BIGSERIAL       PRIMARY KEY,
    code        VARCHAR(10)     UNIQUE NOT NULL,
    nom         VARCHAR(100)    NOT NULL,
    prenom      VARCHAR(100)    NOT NULL,
    secteur     VARCHAR(50),
    color       VARCHAR(7),
    actif       BOOLEAN         DEFAULT TRUE,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_aides_soignants_code ON aides_soignants(code);

-- ── Types de soins ────────────────────────────────────────────────────────────
CREATE TABLE type_soins (
    id               BIGSERIAL    PRIMARY KEY,
    code             VARCHAR(50)  UNIQUE NOT NULL,
    libelle          VARCHAR(255) NOT NULL,
    duree_par_defaut INTEGER,
    actif            BOOLEAN      DEFAULT TRUE,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_type_soins_code ON type_soins(code);

-- ── Patients ──────────────────────────────────────────────────────────────────
CREATE TABLE patients (
    id                    BIGSERIAL    PRIMARY KEY,
    numero_chambre        VARCHAR(20),
    nom                   VARCHAR(100) NOT NULL,
    prenom                VARCHAR(100) NOT NULL,
    etage                 INTEGER,
    statut                VARCHAR(50),
    categorie             VARCHAR(10),
    profil                VARCHAR(100),
    priorite              VARCHAR(20),
    groupe_coucher        VARCHAR(20)  DEFAULT 'NON_DEFINI',
    temps_toilette_lit    INTEGER      DEFAULT 0,
    temps_toilette_vasque INTEGER      DEFAULT 0,
    temps_toilette_moyen  INTEGER      DEFAULT 0,
    temps_wc_moyen        INTEGER      DEFAULT 0,
    temps_coucher_moyen   INTEGER      DEFAULT 0,
    aide_soignant         BOOLEAN      DEFAULT FALSE,
    petit_dejeuner_aide   BOOLEAN      DEFAULT FALSE,
    sans_douche           BOOLEAN      DEFAULT FALSE,
    created_at            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_patients_statut  ON patients(statut);
CREATE INDEX idx_patients_etage   ON patients(etage);
CREATE INDEX idx_patients_categorie ON patients(categorie);

-- ── Planning soins ────────────────────────────────────────────────────────────
CREATE TABLE planning_soins (
    id               BIGSERIAL    PRIMARY KEY,
    patient_id       BIGINT       NOT NULL  REFERENCES patients(id)       ON DELETE CASCADE,
    type_soin_id     BIGINT       NOT NULL  REFERENCES type_soins(id)     ON DELETE RESTRICT,
    aide_soignant_id BIGINT                 REFERENCES aides_soignants(id) ON DELETE SET NULL,
    date_prevue      DATE,
    jour_semaine     VARCHAR(20),
    heure_prevue     VARCHAR(5),
    duree_prevue     INTEGER,
    moment           VARCHAR(50),
    statut           VARCHAR(50)  DEFAULT 'PLANIFIE',
    commentaire      TEXT,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_planning_soins_patient   ON planning_soins(patient_id);
CREATE INDEX idx_planning_soins_aide      ON planning_soins(aide_soignant_id);
CREATE INDEX idx_planning_soins_date      ON planning_soins(date_prevue);
CREATE INDEX idx_planning_soins_statut    ON planning_soins(statut);

-- ── Exécutions de soins ───────────────────────────────────────────────────────
CREATE TABLE execution_soins (
    id                       BIGSERIAL    PRIMARY KEY,
    planning_soin_id         BIGINT                 REFERENCES planning_soins(id)  ON DELETE SET NULL,
    patient_id               BIGINT       NOT NULL  REFERENCES patients(id)         ON DELETE CASCADE,
    type_soin_id             BIGINT       NOT NULL  REFERENCES type_soins(id)       ON DELETE RESTRICT,
    aide_soignant_id         BIGINT                 REFERENCES aides_soignants(id)  ON DELETE SET NULL,
    second_aide_soignant_id  BIGINT                 REFERENCES aides_soignants(id)  ON DELETE SET NULL,
    date_execution           DATE,
    heure_execution          VARCHAR(5),
    statut                   VARCHAR(50)  DEFAULT 'PLANIFIE',
    commentaire              TEXT,
    created_at               TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_execution_soins_patient  ON execution_soins(patient_id);
CREATE INDEX idx_execution_soins_planning ON execution_soins(planning_soin_id);
CREATE INDEX idx_execution_soins_date     ON execution_soins(date_execution);
CREATE INDEX idx_execution_soins_statut   ON execution_soins(statut);

-- ── Notes patients ────────────────────────────────────────────────────────────
CREATE TABLE patient_notes (
    id             BIGSERIAL    PRIMARY KEY,
    patient_id     BIGINT       NOT NULL  REFERENCES patients(id) ON DELETE CASCADE,
    auteur_id      BIGINT,
    date_note      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    contenu        TEXT         NOT NULL,
    important      BOOLEAN      DEFAULT FALSE,
    categorie_note VARCHAR(100),
    created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_patient_notes_patient ON patient_notes(patient_id);
CREATE INDEX idx_patient_notes_date    ON patient_notes(date_note);

-- ── Alertes patients ──────────────────────────────────────────────────────────
CREATE TABLE patient_alerts (
    id               BIGSERIAL    PRIMARY KEY,
    patient_id       BIGINT                 REFERENCES patients(id)        ON DELETE CASCADE,
    aide_soignant_id BIGINT                 REFERENCES aides_soignants(id) ON DELETE CASCADE,
    type             VARCHAR(50),
    niveau           VARCHAR(20),
    message          TEXT         NOT NULL,
    date_creation    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    resolue          BOOLEAN      DEFAULT FALSE,
    date_resolution  TIMESTAMP,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_patient_alerts_patient  ON patient_alerts(patient_id);
CREATE INDEX idx_patient_alerts_resolue  ON patient_alerts(resolue);
CREATE INDEX idx_patient_alerts_niveau   ON patient_alerts(niveau);

-- ── Historique patients ───────────────────────────────────────────────────────
CREATE TABLE patient_historique (
    id           BIGSERIAL    PRIMARY KEY,
    patient_id   BIGINT       NOT NULL  REFERENCES patients(id) ON DELETE CASCADE,
    acteur_id    BIGINT,
    type_action  VARCHAR(100),
    description  TEXT,
    date_action  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_patient_historique_patient ON patient_historique(patient_id);
CREATE INDEX idx_patient_historique_date    ON patient_historique(date_action);

-- ── Absences aides-soignants ──────────────────────────────────────────────────
CREATE TABLE aide_soignant_absences (
    id               BIGSERIAL    PRIMARY KEY,
    aide_soignant_id BIGINT       NOT NULL  REFERENCES aides_soignants(id) ON DELETE CASCADE,
    date_debut       DATE         NOT NULL,
    date_fin         DATE         NOT NULL,
    raison           VARCHAR(100),
    statut           VARCHAR(50)  DEFAULT 'ACTIVE',
    commentaire      TEXT,
    created_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_absences_aide ON aide_soignant_absences(aide_soignant_id);
CREATE INDEX idx_absences_date ON aide_soignant_absences(date_debut, date_fin);
