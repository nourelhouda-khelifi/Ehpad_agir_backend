-- ─────────────────────────────────────────────────────────────────────────────
-- EHPAD Agir — Données de référence : types de soins
-- V5 : Remplace le seeding depuis DataInitializer (idempotent via ON CONFLICT)
-- ─────────────────────────────────────────────────────────────────────────────

INSERT INTO type_soins (code, libelle, duree_par_defaut, actif, created_at, updated_at)
VALUES
    ('TOILETTE',        'Toilette complète', 45, true, NOW(), NOW()),
    ('DOUCHE',          'Douche',            60, true, NOW(), NOW()),
    ('AIDE_REPAS',      'Aide repas',        30, true, NOW(), NOW()),
    ('COUCHER',         'Coucher',           30, true, NOW(), NOW()),
    ('MISE_WC',         'Mise WC',           15, true, NOW(), NOW()),
    ('LEVER',           'Lever',             20, true, NOW(), NOW()),
    ('SIESTE',          'Sieste',            60, true, NOW(), NOW()),
    ('PETIT_DEJEUNER',  'Petit déjeuner',    20, true, NOW(), NOW())
ON CONFLICT (code) DO NOTHING;
