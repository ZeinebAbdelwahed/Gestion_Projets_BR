# Gestion des Projets avec Budgets et Ressources

Application web complète de gestion de projets avec suivi budgétaire et attribution des ressources, déployée sur **Google Cloud Platform**.

🌐 **Application en ligne** : https://gestion-frontend-417904183240.europe-west9.run.app  
📡 **API Backend** : https://gestion-backend-417904183240.europe-west9.run.app/swagger-ui.html

---

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
| MySQL | 8.4 | Base de données (Cloud SQL GCP) |
| Maven | 3.9.x | Build tool |

### Frontend
| Technologie | Version | Usage |
|-------------|---------|-------|
| Angular | 17 | Framework frontend |
| Angular SSR | 17 | Server-Side Rendering (Node.js/Express) |
| TypeScript | 5.x | Langage |
| RxJS | 7.x | Programmation réactive |

### DevOps & Cloud
| Technologie | Usage |
|-------------|-------|
| Docker | Conteneurisation (multi-stage builds) |
| Docker Compose | Orchestration locale multi-conteneurs |
| GCP Cloud Run | Déploiement serverless backend & frontend |
| GCP Cloud SQL | Base de données MySQL managée |
| GCP Artifact Registry | Registre d'images Docker |

## 📁 Structure du Projet

```
Gestion_Projets_BR/
├── backend/                          # API REST Spring Boot
│   ├── src/main/java/com/gestion/projets/
│   │   ├── config/                   # CORS, OpenAPI, Data initializer, MapStruct
│   │   ├── controller/               # Contrôleurs REST
│   │   ├── convertor/                # Convertisseurs MapStruct (Entity ↔ DTO)
│   │   ├── dto/                      # Data Transfer Objects
│   │   ├── entity/                   # Entités JPA
│   │   ├── repository/               # Repositories Spring Data
│   │   └── service/                  # Logique métier
│   ├── src/main/resources/
│   │   ├── application.properties         # Config par défaut (H2)
│   │   └── application-gcp.properties     # Config GCP (Cloud SQL, CORS)
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   └── gestion-projets-ui/           # Application Angular 17 SSR
│       ├── src/
│       │   ├── app/
│       │   │   ├── components/       # Composants partagés
│       │   │   └── pages/            # Pages de l'application
│       │   └── environments/
│       │       ├── environment.ts           # Config développement local
│       │       └── environment.prod.ts      # Config production (GCP)
│       ├── angular.json
│       ├── server.ts                 # Serveur Express SSR
│       └── package.json
│   └── Dockerfile
├── docker-compose.yml                # Orchestration locale
└── README.md
```

---

## 🏗 Architecture

```
┌──────────────────────────────┐     HTTPS     ┌──────────────────────────┐     JPA      ┌───────────────────────┐
│   Angular 17 SSR             │ ────────────▶ │  Spring Boot 3.2.5       │ ───────────▶ │  Cloud SQL MySQL 8.4  │
│   Node.js/Express (Port 4000)│   REST API    │  (Port 8080)             │              │  (GCP europe-west9)   │
│                              │               │                          │              │                       │
│  - Dashboard (SSR)           │               │  - Controllers REST       │              │  - projets            │
│  - Projets                   │               │  - Services métier        │              │  - taches             │
│  - Tâches                    │               │  - DTOs + Validation      │              │  - ressources         │
│  - Ressources                │               │  - Repositories JPA       │              │  - employes           │
│  - Employés                  │               │  - Swagger UI             │              │                       │
│  - Rapports                  │               │  - CORS configuré         │              │                       │
└──────────────────────────────┘               └──────────────────────────┘              └───────────────────────┘
        GCP Cloud Run                                 GCP Cloud Run                            GCP Cloud SQL
   europe-west9 (Port 4000)                      europe-west9 (Port 8080)
```

---

## 🚀 Déploiement GCP (Cloud Run)

### URLs de production
| Service | URL |
|---------|-----|
| Frontend | https://gestion-frontend-417904183240.europe-west9.run.app |
| Backend API | https://gestion-backend-417904183240.europe-west9.run.app |
| Swagger UI | https://gestion-backend-417904183240.europe-west9.run.app/swagger-ui.html |

### Infrastructure GCP
- **Projet GCP** : `gestionprojetbr`
- **Région** : `europe-west9` (Paris)
- **Artifact Registry** : `europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/`
- **Cloud SQL** : Instance `gestionprojetbr:europe-west9:gestion-projet-db` (MySQL 8.4)

### Rebuilder et redéployer le backend
```powershell
docker build -t europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/backend:latest ./backend
docker push europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/backend:latest
gcloud run deploy gestion-backend `
  --image=europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/backend:latest `
  --region=europe-west9 --platform=managed --allow-unauthenticated `
  --set-env-vars="SPRING_PROFILES_ACTIVE=gcp"
```

### Rebuilder et redéployer le frontend
```powershell
docker build --build-arg BACKEND_URL=https://gestion-backend-417904183240.europe-west9.run.app `
  -t europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/frontend:latest ./frontend
docker push europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/frontend:latest
gcloud run deploy gestion-frontend `
  --image=europe-west9-docker.pkg.dev/gestionprojetbr/gestion-projets/frontend:latest `
  --region=europe-west9 --platform=managed --allow-unauthenticated --port=4000
```

---

## 🐳 Exécution Locale avec Docker Compose

```bash
docker-compose up --build
```

| Service | URL locale |
|---------|-----------|
| Frontend | http://localhost:4200 |
| Backend API | http://localhost:8081/api |
| Swagger UI | http://localhost:8081/swagger-ui.html |
| MySQL | localhost:3307 |

```bash
# Arrêter
docker-compose down

# Supprimer les données
docker-compose down -v
```

---

## 💻 Développement Local (sans Docker)

### Backend
```bash
cd backend
mvn spring-boot:run
# API : http://localhost:8080
# Swagger : http://localhost:8080/swagger-ui.html
```

### Frontend
```bash
cd frontend/gestion-projets-ui
npm install --legacy-peer-deps
ng serve
# App : http://localhost:4200
```

---

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

---

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


---

## 📊 Fonctionnalités Principales

1. **Dashboard** — Vue d'ensemble avec statistiques, projets récents et tâches
2. **Gestion des Projets** — CRUD, filtres par statut, vue détaillée avec ressources
3. **Gestion des Tâches** — CRUD, filtres par état/priorité, attribution responsable et ressources
4. **Gestion des Ressources** — CRUD avec suivi de disponibilité et coût
5. **Gestion des Employés** — Annuaire complet avec rôles et équipes
6. **Attribution des Ressources** — Par projet et par tâche, avec validation du budget
7. **Rapports Financiers** — Budget vs coûts, pourcentage d'utilisation, détail par ressource
8. **Suivi Avancement** — Pourcentage de tâches terminées par projet

---

## 👤 Auteur

Projet réalisé dans le cadre du cours de développement web.
