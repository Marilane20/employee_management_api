# Employee Management API

REST API de gestion des employés, des départements et des projets, développée avec **Java 21**, **Spring Boot**, **Spring Data JPA** et **PostgreSQL**.

## Fonctionnalités

- Gestion des employés, départements et projets.
- Affectation des employés aux projets.
- Pagination et tri des résultats.
- Validation des données et gestion centralisée des erreurs.
- Migrations de base de données avec Flyway.

## Architecture du projet

```text
com.example.employee_management_api
├── controller   # Points d'entrée REST et validation
├── service      # Logique métier et transactions
├── repository   # Accès aux données avec Spring Data JPA
├── entity       # Entités JPA
├── dto          # Objets de transfert, notamment les records Java 21
├── mapper       # Conversion entre entités et DTO
└── exception    # Gestion centralisée des exceptions
```

## Principes techniques

- **Séparation du domaine et de l'API :** les entités JPA ne sont pas exposées directement aux clients HTTP.
- **Gestion du schéma :** `ddl-auto: validate` et migrations Flyway.
- **Transactions :** `@Transactional(readOnly = true)` pour les lectures adaptées et transactions pour les écritures.
- **Gestion des erreurs :** `@RestControllerAdvice` et réponses au format Problem Details (RFC 7807), selon la configuration.
- **Pagination :** utilisation de `Pageable` pour limiter les données chargées.

## Règles métier

- Chaque employé doit être rattaché à un département existant.
- L'adresse d'un employé est persistée en cascade avec celui-ci.
- Un département contenant encore des employés ne peut pas être supprimé (`422 Unprocessable Entity`).
- Un employé qui supervise une équipe active ne peut pas être supprimé.
- Le matricule (`employeeId`), l'e-mail et le téléphone doivent être uniques.
- Les noms des départements et des projets sont uniques sans distinction entre majuscules et minuscules.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway
- Maven
- Docker (facultatif)

## Prérequis

- JDK Java 21
- Maven 3.9 ou supérieur
- PostgreSQL local ou accessible
- Docker, si nécessaire

## Installation et démarrage

### 1. Créer la base de données

```sql
CREATE DATABASE employee_management;
```

### 2. Configurer la connexion

**Linux / macOS (Bash)**

```bash
export DB_URL=jdbc:postgresql://localhost:5432/employee_management
export DB_USERNAME=postgres
export DB_PASSWORD=votre_mot_de_passe
```

**Windows PowerShell**

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/employee_management"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="votre_mot_de_passe"
```

Remplacez `votre_mot_de_passe` par votre mot de passe PostgreSQL. Ne publiez jamais vos identifiants réels dans le dépôt.

### 3. Compiler et démarrer

À la racine du projet :

```bash
mvn clean spring-boot:run
```

L'API est accessible à l'adresse :

```text
http://localhost:8080/api/v1
```

## Endpoints principaux

| Méthode | Route | Description | Succès |
|---|---|---|---|
| `POST` | `/api/v1/departments` | Créer un département | `201 Created` |
| `GET` | `/api/v1/departments` | Lister les départements avec pagination | `200 OK` |
| `GET` | `/api/v1/departments/{id}` | Consulter un département et ses membres | `200 OK` |
| `DELETE` | `/api/v1/departments/{id}` | Supprimer un département vide | `204 No Content` |
| `POST` | `/api/v1/projects` | Créer un projet | `201 Created` |
| `GET` | `/api/v1/projects` | Lister les projets | `200 OK` |
| `POST` | `/api/v1/projects/{pId}/employees/{eId}` | Affecter un employé à un projet | `200 OK` |
| `POST` | `/api/v1/employees` | Créer un employé | `201 Created` |
| `GET` | `/api/v1/employees` | Rechercher les employés avec pagination et tri | `200 OK` |
| `GET` | `/api/v1/employees/{id}` | Consulter le profil d'un employé | `200 OK` |
| `DELETE` | `/api/v1/employees/{id}` | Supprimer un employé autorisé | `204 No Content` |

Les paramètres de pagination et de tri peuvent inclure `page`, `size`, `sortBy` et `direction`, selon l'implémentation.

## Codes HTTP

| Code | Signification |
|---|---|
| `200 OK` | Requête exécutée avec succès |
| `201 Created` | Ressource créée |
| `204 No Content` | Opération réussie sans contenu de réponse |
| `400 Bad Request` | Requête invalide ou échec de validation |
| `404 Not Found` | Ressource introuvable |
| `409 Conflict` | Conflit, par exemple une valeur unique déjà utilisée |
| `422 Unprocessable Entity` | Règle métier non respectée |

## Tests de l'API

Le fichier de requêtes HTTP se trouve ici :

```text
requests/api-tests.http
```

Exécutez les requêtes depuis IntelliJ IDEA ou VS Code avec l'extension **REST Client** pour tester les parcours nominaux et les cas d'erreur, notamment les contraintes d'unicité et les suppressions interdites.

---
