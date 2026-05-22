# Architecture et modèle de données - Ehpad

Ce document propose une base d'architecture pour relier le frontend Vue `Ehpad_Agir` au backend Spring Boot `Ehpad`, avec un modèle de données adapté aux besoins visibles dans les mocks du frontend.

## 1. Vue d'ensemble de l'architecture

Architecture simple et robuste :

- Frontend : Vue 3 + Vite
- Backend : Spring Boot 4, Java 21
- Base de données : PostgreSQL
- Authentification : Spring Security
- Documentation API : Swagger/OpenAPI

Flux cible :

- Le frontend consomme une API REST.
- Le backend expose les ressources métier.
- PostgreSQL stocke patients, soignants, soins, planning, notes et alertes.

## 2. Structure recommandée du backend

Découpage de packages Spring Boot :

- `config` : sécurité, CORS, OpenAPI, Jackson, etc.
- `controller` : endpoints REST
- `service` : logique métier
- `repository` : accès JPA
- `entity` : entités persistées
- `dto` : objets échangés avec le frontend
- `mapper` : conversion entity <-> dto
- `exception` : erreurs métier et gestion globale
- `security` : configuration auth et rôles

Structure type :

- `com.example.Ehpad.EhpadApplication`
- `com.example.Ehpad.config`
- `com.example.Ehpad.controller`
- `com.example.Ehpad.dto`
- `com.example.Ehpad.mapper`
- `com.example.Ehpad.repository`
- `com.example.Ehpad.security`
- `com.example.Ehpad.service`

## 3. Entités métier principales

Les mocks du frontend montrent un produit centré sur :

- les patients
- les aides-soignants
- le planning hebdomadaire des soins
- les alertes métier
- les notes et l'historique d'actions

### 3.1 Patient

Représente un résident.

Champs principaux :

- `id`
- `numero_chambre` ou `chambre`
- `nom`
- `prenom`
- `etage`
- `statut`
- `categorie` comme enum ou code: `cat1`, `cat2`, `cat3`, `cat4`
- `profil` comme code métier ou enum côté application
- indicateurs de dépendance et besoins de soin
- temps moyens des soins

### 3.2 AideSoignant

Représente un membre du personnel.

Champs principaux :

- `id`
- `code` comme `SE1`, `SE2`, `SC1`, `SC2`, `SG`
- `nom`
- `prenom`
- `secteur`
- `color`
- `actif`

### 3.3 Soin

Normalise les soins : douche, toilette, WC, coucher, lever, repas, sieste, petit déjeuner.

Champs principaux :

- `id`
- `code`
- `libelle`
- `duree_par_defaut`
- `actif`

### 3.4 PlanningSoin

Représente une planification récurrente ou datée pour un patient.

Champs principaux :

- `id`
- `patient_id`
- `type_soin_id`
- `aide_soignant_id`
- `date_prevue`
- `jour_semaine`
- `heure_prevue`
- `duree_prevue`
- `moment` (`matin`, `soir`)
- `statut` (`planifie`, `effectue`, `annule`, `reporte`)
- `commentaire`

### 3.5 ExecutionSoin / HistoriqueSoin

Trace ce qui a réellement été fait.

Champs principaux :

- `id`
- `planning_soin_id` si lié à un planning
- `patient_id`
- `type_soin_id`
- `aide_soignant_id`
- `date_execution`
- `heure_execution`
- `statut`
- `commentaire`

### 3.6 NotePatient

Pour les notes visibles dans le profil patient.

Champs principaux :

- `id`
- `patient_id`
- `auteur_id`
- `date_note`
- `contenu`
- `important`
- `categorie_note` ou `type_note`

### 3.7 Alerte

Pour les alertes métier comme douche manquante ou surcharge.

Champs principaux :

- `id`
- `patient_id` nullable
- `aide_soignant_id` nullable
- `type`
- `niveau`
- `message`
- `date_creation`
- `resolue`
- `date_resolution`

### 3.8 HistoriqueAction

Si vous voulez un journal plus général que les soins.

Champs principaux :

- `id`
- `patient_id`
- `acteur_id`
- `type_action`
- `description`
- `date_action`

## 4. Tables recommandées au départ

Pour un MVP propre, je recommande ce noyau :

1. `users`
2. `roles`
3. `user_roles`
4. `patients`
5. `aides_soignants`
6. `type_soins`
7. `planning_soins`
8. `execution_soins`
9. `patient_notes`
10. `patient_alerts`
11. `patient_historique`

## 5. Relations principales

Relations conseillées :

- Un `patient` porte sa `categorie` et son `profil` directement dans la table, sans table de référence dédiée.
- Un `planning_soin` appartient à un `patient` et à un `type_soin`.
- Un `planning_soin` est assigné à un `aide_soignant`, donc le soignant est lié au soin et non directement au patient.
- Un `execution_soin` peut être relié à un `planning_soin`.
- Une `note` appartient à un `patient`.
- Une `alerte` peut concerner un `patient` ou un `aide_soignant`.

## 6. Mapping direct avec le frontend actuel

Les fichiers mockés donnent déjà les objets à servir depuis l'API :

- `mockPatients` -> `patients`
- `mockAidesSoignants` -> `aides_soignants`
- `mockPlanningSemaine19` / `mockPlanningSemaine20` -> `planning_soins`
- `mockSoinsByPatient` -> `execution_soins`, `patient_notes`, `patient_historique`
- `mockAlertes` -> `patient_alerts`
- `mockStats` -> vues ou agrégations SQL

## 7. Découpage API conseillé

Endpoints REST minimaux :

- `GET /api/patients`
- `GET /api/patients/{id}`
- `GET /api/patients/{id}/notes`
- `GET /api/patients/{id}/historique`
- `GET /api/aides-soignants`
- `GET /api/plannings?semaine=...`
- `POST /api/plannings`
- `GET /api/alertes`
- `GET /api/stats/dashboard`

## 8. Ordre de réalisation conseillé

1. Créer les entités de base : patient, aide soignant, type de soin.
2. Créer le planning et l'historique des soins.
3. Créer notes et alertes.
4. Ajouter les endpoints de lecture.
5. Brancher le frontend sur l'API.
6. Ajouter auth et rôles ensuite, si nécessaire.

## 9. Recommandation pratique

Pour démarrer vite, je conseille de ne pas modéliser trop tôt les écrans comme tables séparées. Le bon noyau est :

- `patients`
- `aides_soignants`
- `type_soins`
- `planning_soins`
- `execution_soins`
- `patient_notes`
- `patient_alerts`

Le reste peut être ajouté après validation du MVP.
