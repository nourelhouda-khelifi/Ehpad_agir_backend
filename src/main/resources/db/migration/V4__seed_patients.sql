-- ─────────────────────────────────────────────────────────────────────────────
-- EHPAD Agir — Données réelles des résidents (production)
-- V4 : Insertion de tous les résidents issus du planning papier
--
-- Colonnes temps (en minutes) :
--   temps_toilette_lit    = aide supplémentaire matin (col4 planning)
--   temps_toilette_moyen  = durée toilette principale (SE1 MATIN)
--   temps_wc_moyen        = aide WC / petit-déjeuner (col5 planning)
--   temps_coucher_moyen   = durée coucher soir (Temps CouchersL)
--
-- groupe_coucher :
--   HELIOS        = Coucher Hélios 18h30-19h30
--   GRANDE_SALLE  = Coucher Grande Salle 19h30-20h30
--   NON_DEFINI    = non encore déterminé
-- ─────────────────────────────────────────────────────────────────────────────

INSERT INTO patients (
    numero_chambre, nom, prenom, etage,
    temps_toilette_lit, temps_toilette_moyen, temps_wc_moyen, temps_coucher_moyen,
    aide_soignant, petit_dejeuner_aide, sans_douche,
    groupe_coucher,
    created_at, updated_at
) VALUES

-- ── 1er étage — B34 ──────────────────────────────────────────────────────────
('11S',  'Agasse',        'Marie-Rose',    1,  0, 15,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('12S',  'Cormier',       'Elyane',        1,  0, 15,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('13S',  'Madoire',       '',              1,  0, 15,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('14S',  'Marysaël',      'Jacqueline',    1,  0, 25,  0, 15, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('15S',  'Boyer',         'Jeanine',       1, 15,  0,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('16S',  'Lopez',         '',              1, 15,  0,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('17S',  'Gence',         'Simone',        1,  0,  5,  0,  5, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('18S',  'Culié',         '',              1,  0,  5,  0,  5, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('19S',  'Raby',          '',              1,  0, 20,  0, 20, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('20S',  'Tronca',        '',              1,  0, 15,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('21S',  'Vorillier',     'Odette',        1, 15, 25, 10, 10, true, true,  false, 'HELIOS',       NOW(), NOW()),
('22S',  'Toulouse',      '',              1,  0, 30,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('23S',  'Cavailles',     'Jacqueline',    1,  0,  5,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('24S',  'Pires',         'Maria',         1,  0, 15,  0, 10, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('25S',  'Serres',        'Christian',     1,  0,  0,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('26S',  'Conan',         'Anne-Marie',    1, 10, 25,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('27S',  'Nguyen',        'Nicole',        1,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('28S',  'Moulis',        'Jacques',       1, 20, 25,  5, 20, true, true,  false, 'HELIOS',       NOW(), NOW()),
('29S',  'Defoor',        'Alice',         1,  0, 10,  0, 15, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('30S',  'Devez',         '',              1,  0, 15,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('70S',  'Loup',          '',              1,  0, 25,  0, 15, true, false, false, 'HELIOS',       NOW(), NOW()),

-- ── 2ème étage — B34 ─────────────────────────────────────────────────────────
('02PD', 'Imart',         '',              2,  0,  0,  0,  5, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('31S',  'Baez',          'Paulette',      2,  0, 20,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('32S',  'Daydé',         '',              2,  0, 20,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('33S',  'Cassan',        '',              2,  0, 10,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('34S',  'Frances',       'Aline',         2,  0, 20,  0,  0, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('35S',  'Cavailles',     'Michel',        2,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('36S',  'Bernard',       'René',          2, 30, 25,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('37S',  'Guy',           'Robert',        2, 20, 30, 15, 10, true, true,  false, 'NON_DEFINI',   NOW(), NOW()),
('38S',  'Ibanez',        'Maria',         2, 15, 15,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('39',   'Martin',        'Nicole',        2,  0, 30,  0, 15, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('40S',  'Salvayre',      'Marie',         2, 30, 30, 15,  0, true, true,  false, 'GRANDE_SALLE', NOW(), NOW()),
('41S',  'Tequi',         'Paulette',      2,  0, 20,  5, 10, true, true,  false, 'HELIOS',       NOW(), NOW()),
('42S',  'Vialelle',      'Yvette',        2,  0, 20,  0,  0, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('43S',  'Colzato',       '',              2,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('44S',  'Gigot',         'Claude',        2,  0, 15,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('45S',  'Rouanet',       'Marie-Thérèse', 2,  0, 15,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('46S',  'Cariou',        'Georgette',     2,  0, 15,  0, 10, true, false, false, 'HELIOS',       NOW(), NOW()),
('47',   'Genin',         'Eugène',        2,  0, 10,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('48S',  'Alonzo',        '',              2,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('49S',  'Ruiz',          'Pierre',        2,  0, 15,  5, 10, true, true,  false, 'GRANDE_SALLE', NOW(), NOW()),
('D01',  'Micheneau',     'Huguette',      2,  0, 20,  0, 15, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),

-- ── Chambre double D03 (physiquement B34 1er étage, section 3ème) ─────────────
('D03',  'Ortiz',         'Ernest',        1,  0,  0,  0, 15, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('D03',  'Ortiz',         'Gilberte',      1,  0, 25,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),

-- ── 3ème étage — B34 ─────────────────────────────────────────────────────────
('50',   'Garcia',        'Juliette',      3,  0,  0,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('51S',  'Savel',         'Michelle',      3,  0, 10,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('52S',  'Bonnery',       '',              3,  0, 15,  0,  0, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('53S',  'Raout',         '',              3,  0, 15,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('54S',  'Bonnet',        'Colette',       3,  0,  0,  0,  0, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('55S',  'Dallongeville', 'Huguette',      3,  0,  0,  0,  0, true, false, false, 'NON_DEFINI',   NOW(), NOW()),
('56S',  'Burgeat',       '',              3,  0, 20,  0, 20, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('57S',  'Gleizes',       'Yvonne',        3,  0, 20,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('58S',  'Valery',        'Anne-Marie',    3,  0,  5,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('59S',  'Gabillard',     '',              3,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('60S',  'Skudlarek',     'Françoise',     3,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('61S',  'Jaures',        'Pierre',        3,  0,  5,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('62S',  'Bauguil',       'Irène',         3,  0, 20,  0, 15, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('63S',  'Gautier',       'Suzanne',       3,  0,  5,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('64S',  'Salvan',        'Bernadette',    3,  0,  0,  0,  5, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('65D',  'Villeneuve',    'Arlette',       3,  0, 15,  0, 10, true, false, false, 'GRANDE_SALLE', NOW(), NOW()),
('66S',  'Blanc',         'Fernande',      3,  0, 10,  0,  0, true, false, false, 'GRANDE_SALLE', NOW(), NOW());


-- ─────────────────────────────────────────────────────────────────────────────
-- Notes de soins issues du planning papier
-- ─────────────────────────────────────────────────────────────────────────────
INSERT INTO patient_notes (patient_id, contenu, important, categorie_note, created_at, updated_at)
VALUES
-- 1er étage
((SELECT id FROM patients WHERE numero_chambre='15S' AND nom='Boyer'),               '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='16S' AND nom='Lopez'),               '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='21S' AND nom='Vorillier'),           'Pas de 2ème soignant',                                 true,  'ORGANISATION', NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='23S' AND prenom='Jacqueline'),       '15 minutes le jour de douche',                         false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='26S' AND nom='Conan'),               'Patiente seule',                                       false, 'OBSERVATION',  NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='28S' AND nom='Moulis'),              'Problème pour la douche',                              true,  'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='70S' AND nom='Loup'),               'Tombe — trop lourde pour lève-personne',               true,  'SECURITE',     NOW(), NOW()),
-- 2ème étage
((SELECT id FROM patients WHERE numero_chambre='31S' AND nom='Baez'),               '2 à 3 passages requis',                                false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='32S' AND nom='Daydé'),              '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='34S' AND nom='Frances'),            '3 à 4 passages — linge de toilette + draps',           false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='35S' AND prenom='Michel'),          '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='36S' AND nom='Bernard'),            'Lever avant petit-déjeuner, un seul passage',          false, 'ORGANISATION', NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='37S' AND nom='Guy'),                '2 passages avec rasage tous les jours',                false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='39'  AND nom='Martin'),             'Patiente seule',                                       false, 'OBSERVATION',  NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='46S' AND nom='Cariou'),             '3 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='49S' AND nom='Ruiz'),               'Stimuler — plusieurs passages nécessaires',            false, 'SOIN',         NOW(), NOW()),
-- 3ème étage
((SELECT id FROM patients WHERE numero_chambre='51S' AND nom='Savel'),              'Le jour de douche : effectuer 2 fois',                 false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='53S' AND nom='Raout'),              '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='55S' AND nom='Dallongeville'),      '2 passages requis',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='57S' AND nom='Gleizes'),            'Douche 45 minutes',                                    false, 'SOIN',         NOW(), NOW()),
((SELECT id FROM patients WHERE numero_chambre='60S' AND nom='Skudlarek'),          'Demande aide pour la douche',                          false, 'SOIN',         NOW(), NOW());
