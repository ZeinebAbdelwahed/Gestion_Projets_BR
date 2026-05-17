export interface Projet {
  id?: number;
  nom: string;
  dateDebut: string;
  dateFin: string;
  budget: number;
  statut: string;
  ressourceIds?: number[];
  coutTotal?: number;
  budgetRestant?: number;
}
