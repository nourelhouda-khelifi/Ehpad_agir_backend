-- Schéma initial - Ehpad (compatible H2)

-- ============================================
-- USERS & SECURITY
-- ============================================

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(10) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    secteur VARCHAR(50),
    color VARCHAR(7),
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE INDEX idx_aides_soignants_code ON aides_soignants(code);

-- ============================================
-- TYPE DE SOINS
-- ============================================

CREATE TABLE type_soins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) UNIQUE NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    duree_par_defaut INTEGER,
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE INDEX idx_type_soins_code ON type_soins(code);

-- ============================================
-- PATIENTS
-- ============================================

CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero_chambre VARCHAR(20),
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    etage INTEGER,
    statut VARCHAR(50) DEFAULT 'hospitalise' CHECK (statut IN ('hospitalise', 'ambulatoire', 'conge', 'deces')),
    categorie VARCHAR(50) CHECK (categorie IN ('cat1', 'cat2', 'cat3', 'cat4')),
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
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE INDEX idx_patients_statut ON patients(statut);
CREATE INDEX idx_patients_categorie ON patients(categorie);
CREATE INDEX idx_patients_etage ON patients(etage);

-- ============================================
-- PLANNING SOINS
-- ============================================

CREATE TABLE planning_soins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    type_soin_id BIGINT NOT NULL,
    aide_soignant_id BIGINT,
    
    date_prevue DATE,
    jour_semaine VARCHAR(20),
    heure_prevue VARCHAR(5),
    duree_prevue INTEGER,
    
    moment VARCHAR(50) CHECK (moment IN ('matin', 'soir', 'midi', 'nuit')),
    statut VARCHAR(50) DEFAULT 'planifie' CHECK (statut IN ('planifie', 'effectue', 'annule', 'reporte')),
    
    commentaire TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
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
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    planning_soin_id BIGINT,
    patient_id BIGINT NOT NULL,
    type_soin_id BIGINT NOT NULL,
    aide_soignant_id BIGINT,
    
    date_execution DATE,
    heure_execution VARCHAR(5),
    
    statut VARCHAR(50) DEFAULT 'effectue' CHECK (statut IN ('planifie', 'effectue', 'annule', 'reporte')),
    
    commentaire TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
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
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    auteur_id BIGINT,
    
    date_note TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    contenu TEXT NOT NULL,
    
    important BOOLEAN DEFAULT FALSE,
    categorie_note VARCHAR(100),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (auteur_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_patient_notes_patient ON patient_notes(patient_id);
CREATE INDEX idx_patient_notes_date ON patient_notes(date_note);

-- ============================================
-- PATIENT ALERTS
-- ============================================

CREATE TABLE patient_alerts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT,
    aide_soignant_id BIGINT,
    
    type VARCHAR(50) CHECK (type IN ('douche_manquante', 'as_surcharge', 'patient_alerte', 'autre')),
    niveau VARCHAR(50) CHECK (niveau IN ('critique', 'moyen', 'bas')),
    
    message TEXT NOT NULL,
    
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    resolue BOOLEAN DEFAULT FALSE,
    date_resolution TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
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
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    acteur_id BIGINT,
    
    type_action VARCHAR(100),
    description TEXT,
    
    date_action TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (acteur_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_patient_historique_patient ON patient_historique(patient_id);
CREATE INDEX idx_patient_historique_date ON patient_historique(date_action);

-- ============================================
-- AIDE SOIGNANT ABSENCES
-- ============================================

CREATE TABLE aide_soignant_absences (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aide_soignant_id BIGINT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    raison VARCHAR(100) CHECK (raison IN ('MALADIE', 'CONGE', 'FORMATION', 'AUTRE')),
    statut VARCHAR(50) DEFAULT 'ACTIVE' CHECK (statut IN ('ACTIVE', 'ANNULEE')),
    commentaire TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    FOREIGN KEY (aide_soignant_id) REFERENCES aides_soignants(id) ON DELETE CASCADE
);

CREATE INDEX idx_aide_soignant_absences_aide_soignant ON aide_soignant_absences(aide_soignant_id);
CREATE INDEX idx_aide_soignant_absences_date ON aide_soignant_absences(date_debut, date_fin);

-- Rôles (created by DataInitializer if needed)
-- Types de soins (created by DataInitializer: TOILETTE, DOUCHE, PANSEMENT, INJECTION, AIDE_REPAS)
-- Aides-soignants (created by DataInitializer: SE1, SC1)
-- Patients (created by DataInitializer)
-- ExecutionSoins (created via frontend API or this migration)
