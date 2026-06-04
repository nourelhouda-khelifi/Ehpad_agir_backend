-- Un aide-soignant est identifié par son code et sa couleur.
-- Le nom et prénom ne sont pas obligatoires dans le formulaire.
ALTER TABLE aides_soignants ALTER COLUMN nom    DROP NOT NULL;
ALTER TABLE aides_soignants ALTER COLUMN prenom DROP NOT NULL;
