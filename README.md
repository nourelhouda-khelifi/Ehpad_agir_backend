# Ehpad

Projet Spring Boot connecté à PostgreSQL via Docker.

## Configuration base de données

L'application lit la configuration de la base depuis les variables d'environnement suivantes :

- `DB_HOST=localhost`
- `DB_PORT=5432`
- `DB_NAME=ehpad`
- `DB_USER=ehpad`
- `DB_PASSWORD=ehpad`

Un exemple est disponible dans [.env.example](.env.example).

## Lancer PostgreSQL avec Docker

Depuis la racine du projet :

```bash```

Pour arrêter le conteneur :

```bash
docker compose down
```

## Lancer le projet Spring Boot

Avec le wrapper Maven :

```bash
.\mvnw.cmd spring-boot:run
```

Pour exécuter les tests :

```bash
.\mvnw.cmd test
```

## Swagger et endpoint de verification

Une fois l'application demarree, Swagger UI est disponible ici :

- http://localhost:8080/swagger-ui/index.html

L'endpoint de verification est disponible ici :

- GET http://localhost:8080/api/ping

Swagger et l'endpoint `/api/ping` sont accessibles sans authentification.

La page d'accueil `/` redirige vers Swagger UI.

Reponse attendue :

```json
{
	"message": "pong",
	"timestamp": "2026-05-21T00:00:00Z"
}
```

## Rappel

- La base PostgreSQL doit être démarrée avant l'application.
- Les tests utilisent H2 en mémoire, donc ils ne dépendent pas du conteneur PostgreSQL.

## Dépannage Docker sous Windows

Si `docker compose up -d` renvoie une erreur du type `open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified`, cela veut dire que le moteur Docker n'est pas disponible.

Vérifie les points suivants :

- Docker Desktop est bien installé.
- Docker Desktop est bien démarré avant d'exécuter la commande.
- Le backend Linux de Docker est actif.

Quand Docker Desktop est lancé, relance :

```bash
docker compose up -d
```