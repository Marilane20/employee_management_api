# Employee Management API

API REST d'entreprise développée avec **Java 21** et **Spring Boot 4.x**, dédiée à la gestion des employés, départements et projets d'une organisation. Le projet applique les principes de l'architecture en couches, l'isolation du domaine via des DTOs immuables (Records), la validation stricte des données et la gestion des migrations avec Flyway.

---

## Stack Technique

* **Langage** : Java 21 (LTS)
* **Framework** : Spring Boot 4.x
  * Spring Data JPA
  * Spring Web
  * Spring Validation (Hibernate Validator)
* **Base de données** : 
  * PostgreSQL (Développement / Production)
  * H2 Database (Tests d'intégration)
* **Gestion des migrations** : Flyway
* **Outil de build** : Maven
* **Outillage** : Lombok

---

## Architecture Logicielle & Bonnes Pratiques

Le projet suit une séparation stricte des responsabilités (SOC) :

```text
com.example.employee_management_api
├── controller     # Points d'entrée REST, validation @Valid, codes HTTP
├── service        # Logique métier, intégrité transactionnelle (@Transactional)
├── repository     # Requêtes Spring Data JPA, pagination et requêtes dérivées
├── entity         # Modèle relationnel JPA (tables SQL)
├── dto            # Objets de transfert immuables (Java 21 Records)
├── mapper         # Traduction explicite Entity <-> DTO (sans magie)
└── exception      # Gestion globale et centralisée des anomalies (@RestControllerAdvice)

Principes Clés ImplémentésIsolation du Domaine : Les entités JPA ne sont jamais exposées directement au client HTTP. Les entrées et sorties passent systématiquement par des DTOs immuables (records).Source de Vérité SQL : Configuration ddl-auto: validate. Les schémas de base de données sont gérés exclusivement par les scripts de migration Flyway.Intégrité Transactionnelle : Transactions optimisées en lecture seule (@Transactional(readOnly = true)) et rollback automatique en écriture en cas d'incident métier.Gestion des Exceptions Centralisée : Réponses standardisées (RFC 7807) avec codes HTTP précis (400 Bad Request, 404 Not Found, 409 Conflict, 422 Unprocessable Entity).Pagination & Tri Optimisés : Utilisation unifiée de Pageable évitant les surcharges mémoire en base de données. Règles Métier ImplémentéesIntégrité Référentielle :Un employé doit obligatoirement être rattaché à un département existant.L'adresse est persistée en cascade via le cycle de vie de l'employé.Gouvernance & Protection des Données :Interdiction de supprimer un département contenant encore des salariés (422 Unprocessable Entity).Interdiction de supprimer un employé s'il est désigné comme superviseur (manager) d'une équipe active.Contrôles d'Unicité :Matricule (employeeId), adresse email et numéro de téléphone uniques par collaborateur.Noms de départements et de projets uniques (insensibles à la casse).🚀 Installation & DémarragePrérequisJava 21 JDK installéDocker ou instance PostgreSQL localeMaven 3.9+1. Configuration de la Base de DonnéesCréez une base PostgreSQL nommée employee_management :SQLCREATE DATABASE employee_management;
Définissez les variables d'environnement suivantes ou ajustez votre fichier application.yml :Bashexport DB_URL=jdbc:postgresql://localhost:5432/employee_management
export DB_USERNAME=postgres
export DB_PASSWORD=votre_mot_de_passe
2. Compilation et LancementExécutez les migrations Flyway et démarrez l'application :Bashmvn clean spring-boot:run
L'API est accessible sur http://localhost:8080/api/v1.📖 Endpoints PrincipauxMéthodeRouteDescriptionCode SuccèsPOST/api/v1/departmentsCréer un département201 CreatedGET/api/v1/departmentsLister les départements (Paginés)200 OKGET/api/v1/departments/{id}Consulter un département (avec ses membres)200 OKDELETE/api/v1/departments/{id}Supprimer un département (si vide)204 No ContentPOST/api/v1/projectsCréer un projet201 CreatedGET/api/v1/projectsLister les projets200 OKPOST/api/v1/projects/{pId}/employees/{eId}Assigner un employé à un projet200 OKPOST/api/v1/employeesEnregistrer un nouvel employé201 CreatedGET/api/v1/employeesRecherche paginée et triée (page, size, sortBy, direction)200 OKGET/api/v1/employees/{id}Consulter le profil complet d'un employé200 OKDELETE/api/v1/employees/{id}Supprimer un employé204 No Content🧪 Tests de l'APIUn fichier exécutable complet est fourni directement à la racine du dépôt :📁 requests/api-tests.httpVous pouvez lancer les tests directement dans IntelliJ IDEA ou VS Code (avec l'extension REST Client) d'un simple clic sur les icônes d'exécution pour valider le flux nominal et les cas d'erreurs (unicité, contraintes métier, suppressions protégées).
