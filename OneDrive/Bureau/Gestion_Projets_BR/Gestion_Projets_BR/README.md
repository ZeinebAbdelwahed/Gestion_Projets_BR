# Gestion des Projets avec Budgets et Ressources

Application web complète de gestion des projets avec suivi budgétaire et attribution des ressources.

## 📋 Description du Projet

Ce projet est une solution de gestion de projets permettant de :
- **Gérer les projets** : CRUD complet avec suivi des dates, budgets et statuts
- **Gérer les tâches** : Création, attribution aux employés, suivi par état et priorité
- **Gérer les ressources** : Inventaire des ressources avec coûts et disponibilité
- **Gérer les employés** : Annuaire des membres de l'équipe avec rôles
- **Attribution des ressources** : Attribuer des ressources aux projets et aux tâches
- **Rapports financiers** : Suivi des coûts par projet avec indicateurs budgétaires

## 🛠 Technologies Utilisées

### Backend
| Technologie | Version | Usage |
|-------------|---------|-------|
| Java | 17 | Langage principal |
| Spring Boot | 3.2.5 | Framework backend |
| Spring Data JPA | 3.2.x | Accès aux données |
| Spring Validation | 3.2.x | Validation des DTOs |
| SpringDoc OpenAPI | 2.3.0 | Documentation API (Swagger) |
| Lombok | Latest | Réduction du boilerplate |
| MySQL | 8.0 | Base de données (production) |
| H2 | Latest | Base de données (développement) |
| Maven | 3.9.x | Build tool |

### Frontend
| Technologie | Version | Usage |
|-------------|---------|-------|
| Angular | 17 | Framework frontend |
| TypeScript | 5.x | Langage |
| RxJS | 7.x | Programmation réactive |

### DevOps
| Technologie | Usage |
|-------------|-------|
| Docker | Conteneurisation |
| Docker Compose | Orchestration multi-conteneurs |
| Nginx | Serveur web frontend |

## 📁 Structure du Projet

```
Gestion_Projets_BR/
├── backend/                    # API REST Spring Boot
│   ├── src/main/java/com/gestion/projets/
│   │   ├── config/            # Configuration (CORS, OpenAPI, Data)
│   │   ├── controller/        # Contrôleurs REST
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── exception/         # Gestion des erreurs
│   │   ├── model/             # Entités JPA
│   │   ├── repository/        # Repositories Spring Data
│   │   └── service/           # Logique métier
│   ├── Dockerfile
│   └── pom.xml
├── frontend/                   # Interface Angular
│   ├── src/app/
│   │   ├── models/            # Interfaces TypeScript
│   │   ├── services/          # Services HTTP
│   │   └── pages/             # Composants de pages
│   ├── Dockerfile
│   └── nginx.conf
├── docker-compose.yml
└── README.md
```

## 🏗 Architecture

```
┌─────────────────┐     HTTP      ┌──────────────────┐     JPA     ┌─────────────┐
│   Angular 17    │ ──────────── │  Spring Boot 3   │ ──────────│   MySQL 8   │
│   (Port 4200)   │   REST API   │   (Port 8080)    │           │ (Port 3306) │
│                 │              │                  │           │             │
│  - Dashboard    │              │  - Controllers   │           │  - projets  │
│  - Projets      │              │  - Services      │           │  - taches   │
│  - Tâches       │              │  - DTOs+Valid.   │           │  - ressources│
│  - Ressources   │              │  - Repositories  │           │  - employes │
│  - Employés     │              │  - Swagger UI    │           │             │
│  - Rapports     │              │                  │           │             │
└─────────────────┘              └──────────────────┘           └─────────────┘
```

## 🚀 Instructions d'Installation et d'Exécution

### Option 1 : Docker (Recommandé)

**Pré-requis :** Docker et Docker Compose installés.

```bash
# Cloner le repository
git clone <url-du-repo>
cd Gestion_Projets_BR

# Lancer toute l'application
docker-compose up --build

# L'application sera accessible sur :
# - Frontend : http://localhost
# - Backend API : http://localhost:8080/api
# - Swagger UI : http://localhost:8080/swagger-ui.html
# - MySQL : localhost:3306
```

Pour arrêter :
```bash
docker-compose down
```

Pour supprimer les données :
```bash
docker-compose down -v
```

### Option 2 : Développement Local

#### Backend
```bash
cd backend

# Lancer avec H2 (base en mémoire)
./mvnw spring-boot:run

# Ou avec Maven installé
mvn spring-boot:run

# API disponible sur http://localhost:8080
# Console H2 : http://localhost:8080/h2-console
# Swagger UI : http://localhost:8080/swagger-ui.html
```

#### Frontend
```bash
cd frontend

# Installer les dépendances
npm install

# Lancer le serveur de développement
ng serve

# Application disponible sur http://localhost:4200
```

## 📡 API Endpoints

### Projets
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/projets` | Lister tous les projets |
| GET | `/api/projets/{id}` | Détail d'un projet |
| POST | `/api/projets` | Créer un projet |
| PUT | `/api/projets/{id}` | Modifier un projet |
| DELETE | `/api/projets/{id}` | Supprimer un projet |
| POST | `/api/projets/{id}/ressources/{resId}` | Attribuer une ressource |
| DELETE | `/api/projets/{id}/ressources/{resId}` | Retirer une ressource |

### Tâches
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/taches` | Lister toutes les tâches |
| GET | `/api/taches/{id}` | Détail d'une tâche |
| GET | `/api/taches/projet/{projetId}` | Tâches d'un projet |
| POST | `/api/taches` | Créer une tâche |
| PUT | `/api/taches/{id}` | Modifier une tâche |
| DELETE | `/api/taches/{id}` | Supprimer une tâche |
| POST | `/api/taches/{id}/ressources/{resId}` | Attribuer une ressource |

### Ressources
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/ressources` | Lister les ressources |
| GET | `/api/ressources/disponibles` | Ressources disponibles |
| POST | `/api/ressources` | Créer une ressource |
| PUT | `/api/ressources/{id}` | Modifier une ressource |
| DELETE | `/api/ressources/{id}` | Supprimer une ressource |

### Employés
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/employes` | Lister les employés |
| POST | `/api/employes` | Créer un employé |
| PUT | `/api/employes/{id}` | Modifier un employé |
| DELETE | `/api/employes/{id}` | Supprimer un employé |

### Rapports Financiers & Avancement
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/rapports/projet/{id}` | Rapport financier d'un projet |
| GET | `/api/rapports/couts` | Suivi global des coûts |
| GET | `/api/rapports/avancement/{id}` | Avancement d'un projet (% tâches) |
| GET | `/api/rapports/avancement` | Avancement global de tous les projets |

## ✅ Validation des Données

L'API utilise **Spring Validator** (Jakarta Validation) pour assurer l'intégrité des données :

- `@NotBlank` — Champs texte obligatoires
- `@Email` — Format email valide
- `@Positive` — Montants strictement positifs
- `@Size` — Longueur des chaînes de caractères
- `@NotNull` — Champs obligatoires

Les erreurs de validation retournent un status **400 Bad Request** avec le détail des champs invalides.

### Règles Métier Validées
- **Budget non dépassable** : L'ajout d'une ressource à un projet est refusé si le coût total dépasse le budget (HTTP 422).
- **Deadline cohérente** : La deadline d'une tâche doit être comprise entre la date de début et la date de fin du projet (HTTP 400).
- **Email unique** : Chaque employé doit avoir une adresse email unique.

## 📊 Fonctionnalités Principales

1. **Dashboard** — Vue d'ensemble avec statistiques, projets récents et tâches
2. **Gestion des Projets** — CRUD, filtres par statut, vue détaillée avec ressources
3. **Gestion des Tâches** — CRUD, filtres par état/priorité, attribution responsable et ressources
4. **Gestion des Ressources** — CRUD avec suivi de disponibilité et coût
5. **Gestion des Employés** — Annuaire complet avec rôles et équipes
6. **Attribution des Ressources** — Par projet et par tâche, avec validation du budget
7. **Rapports Financiers** — Budget vs coûts, pourcentage d'utilisation, détail par ressource
8. **Suivi Avancement** — Pourcentage de tâches terminées par projet

## 👤 Auteur

Projet réalisé dans le cadre du cours de développement web.
