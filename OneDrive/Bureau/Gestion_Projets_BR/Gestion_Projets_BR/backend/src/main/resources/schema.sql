-- A executer sur GCP Cloud SQL (MySQL 8.0)
CREATE DATABASE IF NOT EXISTS gestion_projets CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_projets;

CREATE TABLE IF NOT EXISTS employe (
    id     BIGINT NOT NULL AUTO_INCREMENT,
    nom    VARCHAR(255),
    email  VARCHAR(255) UNIQUE,
    role   VARCHAR(255),
    equipe VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS ressource (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    nom           VARCHAR(255),
    type          VARCHAR(255),
    cout          DECIMAL(19,2),
    disponibilite BIT(1),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS projet (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    nom        VARCHAR(255),
    date_debut DATE,
    date_fin   DATE,
    budget     DECIMAL(19,2),
    statut     VARCHAR(50),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tache (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    description    TEXT,
    etat           VARCHAR(50),
    priorite       VARCHAR(50),
    deadline       DATE,
    projet_id      BIGINT NOT NULL,
    responsable_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_tache_projet      FOREIGN KEY (projet_id)      REFERENCES projet(id)  ON DELETE CASCADE,
    CONSTRAINT fk_tache_responsable FOREIGN KEY (responsable_id) REFERENCES employe(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS projet_ressource (
    projet_id    BIGINT NOT NULL,
    ressource_id BIGINT NOT NULL,
    PRIMARY KEY (projet_id, ressource_id),
    CONSTRAINT fk_pr_projet    FOREIGN KEY (projet_id)    REFERENCES projet(id)    ON DELETE CASCADE,
    CONSTRAINT fk_pr_ressource FOREIGN KEY (ressource_id) REFERENCES ressource(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS tache_ressource (
    tache_id     BIGINT NOT NULL,
    ressource_id BIGINT NOT NULL,
    PRIMARY KEY (tache_id, ressource_id),
    CONSTRAINT fk_tr_tache     FOREIGN KEY (tache_id)     REFERENCES tache(id)     ON DELETE CASCADE,
    CONSTRAINT fk_tr_ressource FOREIGN KEY (ressource_id) REFERENCES ressource(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
