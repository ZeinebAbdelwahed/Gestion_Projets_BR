# ── Étape 1 : Build Angular (production) ─────────────────────────────────────
FROM node:20-alpine AS build
WORKDIR /app

ARG BACKEND_URL=http://localhost:8081

COPY gestion-projets-ui/package*.json ./
RUN npm ci --legacy-peer-deps

COPY gestion-projets-ui/ ./

# Injecte l'URL du backend dans environment.prod.ts avant le build
RUN sed -i "s|BACKEND_URL_PLACEHOLDER|${BACKEND_URL}|g" src/environments/environment.prod.ts

RUN npm run build

# ── Étape 2 : Image d'exécution SSR (Express) ────────────────────────────────
FROM node:20-alpine
WORKDIR /app

COPY --from=build /app/dist/gestion-projets-ui ./dist/gestion-projets-ui

EXPOSE 4000

ENV PORT=4000

CMD ["node", "dist/gestion-projets-ui/server/server.mjs"]
