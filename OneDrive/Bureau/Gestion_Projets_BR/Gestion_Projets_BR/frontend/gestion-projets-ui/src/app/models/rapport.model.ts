export interface AvancementDTO {
  projetId: number;
  projetNom: string;
  statut: string;
  totalTaches: number;
  tachesTerminees: number;
  tachesEnCours: number;
  tachesAFaire: number;
  pourcentageAvancement: number;
}

export interface CoutRessourceDTO {
  ressourceNom: string;
  ressourceType: string;
  cout: number;
}

export interface RapportFinancierDTO {
  projetId: number;
  projetNom: string;
  statut: string;
  budget: number;
  coutTotalRessources: number;
  budgetRestant: number;
  pourcentageUtilise: number;
  nombreTaches: number;
  nombreRessources: number;
  detailCouts: CoutRessourceDTO[];
}
