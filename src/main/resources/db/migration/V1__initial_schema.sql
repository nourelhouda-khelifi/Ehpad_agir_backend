-- Schéma initial - Ehpad

-- ============================================
-- ENUMS
-- ============================================

CREATE TYPE patient_categorie AS ENUM ('cat1', 'cat2', 'cat3', 'cat4');
CREATE TYPE patient_statut AS ENUM ('hospitalise', 'ambulatoire', 'conge', 'deces');
CREATE TYPE soin_moment AS ENUM ('matin', 'soir', 'midi', 'nuit');
CREATE TYPE planning_statut AS ENUM ('planifie', 'effectue', 'annule', 'reporte');
CREATE TYPE alerte_niveau AS ENUM ('critique', 'moyen', 'bas');
CREATE TYPE alerte_type AS ENUM ('douche_manquante', 'as_surcharge', 'patient_alerte', 'autre');

-- ============================================
-- USERS & SECURITY
-- ============================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ============================================
-- AIDES SOIGNANTS
-- ============================================

CREATE TABLE aides_soignants (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    secteur VARCHAR(50),
    color VARCHAR(7),
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_aides_soignants_code ON aides_soignants(code);

-- ============================================
-- TYPE DE SOINS
-- ============================================

CREATE TABLE type_soins (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    duree_par_defaut INTEGER,
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_type_soins_code ON type_soins(code);

-- ============================================
-- PATIENTS
-- ============================================

CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    numero_chambre VARCHAR(20),
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    etage INTEGER,
    statut patient_statut DEFAULT 'hospitalise',
    categorie patient_categorie,
    profil VARCHAR(100),
    
    -- Indicateurs de dépendance et besoins de soin
    temps_toilette_lit INTEGER DEFAULT 0,
    temps_toilette_vasque INTEGER DEFAULT 0,
    temps_toilette_moyen INTEGER DEFAULT 0,
    temps_wc_moyen INTEGER DEFAULT 0,
    temps_coucher_moyen INTEGER DEFAULT 0,
    
    -- Flags de soin
    aide_soignant BOOLEAN DEFAULT FALSE,
    petit_dejeuner_aide BOOLEAN DEFAULT FALSE,
    sans_douche BOOLEAN DEFAULT FALSE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_patients_statut ON patients(statut);
CREATE INDEX idx_patients_categorie ON patients(categorie);
CREATE INDEX idx_patients_etage ON patients(etage);

-- ============================================
-- PLANNING SOINS
-- ============================================

CREATE TABLE planning_soins (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    type_soin_id BIGINT NOT NULL,
    aide_soignant_id BIGINT,
    
    date_prevue DATE,
    jour_semaine VARCHAR(20),
    heure_prevue VARCHAR(5),
    duree_prevue INTEGER,
    
    moment soin_moment,
    statut planning_statut DEFAULT 'planifie',
    
    commentaire TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (type_soin_id) REFERENCES type_soins(id) ON DELETE RESTRICT,
    FOREIGN KEY (aide_soignant_id) REFERENCES aides_soignants(id) ON DELETE SET NULL
);

CREATE INDEX idx_planning_soins_patient ON planning_soins(patient_id);
CREATE INDEX idx_planning_soins_type_soin ON planning_soins(type_soin_id);
CREATE INDEX idx_planning_soins_aide_soignant ON planning_soins(aide_soignant_id);
CREATE INDEX idx_planning_soins_date ON planning_soins(date_prevue);
CREATE INDEX idx_planning_soins_statut ON planning_soins(statut);

-- ============================================
-- EXECUTION SOINS / HISTORIQUE
-- ============================================

CREATE TABLE execution_soins (
    id BIGSERIAL PRIMARY KEY,
    planning_soin_id BIGINT,
    patient_id BIGINT NOT NULL,
    type_soin_id BIGINT NOT NULL,
    aide_soignant_id BIGINT,
    
    date_execution DATE,
    heure_execution VARCHAR(5),
    
    statut planning_statut DEFAULT 'effectue',
    
    commentaire TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (planning_soin_id) REFERENCES planning_soins(id) ON DELETE SET NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (type_soin_id) REFERENCES type_soins(id) ON DELETE RESTRICT,
    FOREIGN KEY (aide_soignant_id) REFERENCES aides_soignants(id) ON DELETE SET NULL
);

CREATE INDEX idx_execution_soins_patient ON execution_soins(patient_id);
CREATE INDEX idx_execution_soins_planning ON execution_soins(planning_soin_id);
CREATE INDEX idx_execution_soins_date ON execution_soins(date_execution);
CREATE INDEX idx_execution_soins_statut ON execution_soins(statut);

-- ============================================
-- PATIENT NOTES
-- ============================================

CREATE TABLE patient_notes (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    auteur_id BIGINT,
    
    date_note TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    contenu TEXT NOT NULL,
    
    important BOOLEAN DEFAULT FALSE,
    categorie_note VARCHAR(100),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (auteur_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_patient_notes_patient ON patient_notes(patient_id);
CREATE INDEX idx_patient_notes_date ON patient_notes(date_note);

-- ============================================
-- PATIENT ALERTS
-- ============================================

CREATE TABLE patient_alerts (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT,
    aide_soignant_id BIGINT,
    
    type alerte_type,
    niveau alerte_niveau,
    
    message TEXT NOT NULL,
    
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolue BOOLEAN DEFAULT FALSE,
    date_resolution TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (aide_soignant_id) REFERENCES aides_soignants(id) ON DELETE CASCADE
);

CREATE INDEX idx_patient_alerts_patient ON patient_alerts(patient_id);
CREATE INDEX idx_patient_alerts_aide_soignant ON patient_alerts(aide_soignant_id);
CREATE INDEX idx_patient_alerts_resolue ON patient_alerts(resolue);
CREATE INDEX idx_patient_alerts_niveau ON patient_alerts(niveau);

-- ============================================
-- PATIENT HISTORIQUE
-- ============================================

CREATE TABLE patient_historique (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    acteur_id BIGINT,
    
    type_action VARCHAR(100),
    description TEXT,
    
    date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (acteur_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_patient_historique_patient ON patient_historique(patient_id);
CREATE INDEX idx_patient_historique_date ON patient_historique(date_action);

-- ============================================
-- DONNÉES D'INITIALISATION
-- ============================================

-- Rôles
INSERT INTO roles (code, libelle, description) VALUES 
('ADMIN', 'Administrateur', 'Accès complet'),
('MANAGER', 'Manager', 'Gestion de l''équipe et planning'),
('SOIGNANT', 'Aide-soignant', 'Consultation et saisie de soins');

-- Types de soins
INSERT INTO type_soins (code, libelle, duree_par_defaut) VALUES 
('douche', 'Douche', 30),
('toilette', 'Toilette', NULL),
('wc', 'WC', NULL),
('coucher', 'Coucher', NULL),
('lever', 'Lever', 20),
('repas', 'Repas', NULL),
('sieste', 'Sieste', 60),
('petit_dejeuner', 'Petit déjeuner', 25);

-- Aides-soignants (d'après mockAidesSoignants)
INSERT INTO aides_soignants (code, nom, prenom, secteur, color, actif) VALUES 
('SE1', 'Martin', 'Claire', 'Est', '#1D9E75', TRUE),
('SE2', 'Dubois', 'Marie', 'Est', '#E24B4A', TRUE),
('SC1', 'Bernard', 'Julie', 'Centre', '#378ADD', TRUE),
('SC2', 'Petit', 'Sophie', 'Centre', '#5DCAA5', TRUE),
('SG', 'Robert', 'Anne', 'Général', '#888780', TRUE);
