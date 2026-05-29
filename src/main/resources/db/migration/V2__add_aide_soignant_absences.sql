-- Migration V2: Ajouter la table aide_soignant_absences

CREATE TABLE aide_soignant_absences (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aide_soignant_id BIGINT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    raison VARCHAR(50) NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    commentaire VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (aide_soignant_id) REFERENCES aide_soignants(id)
);

CREATE INDEX idx_aide_soignant_id ON aide_soignant_absences(aide_soignant_id);
CREATE INDEX idx_date_range ON aide_soignant_absences(date_debut, date_fin);
