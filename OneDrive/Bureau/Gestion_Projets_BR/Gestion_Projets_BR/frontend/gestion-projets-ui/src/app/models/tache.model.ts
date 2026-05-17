export interface Tache {
  id?: number;
  projetId: number;
  projetNom?: string;
  responsableId?: number;
  responsableNom?: string;
  description: string;
  etat: string;
  priorite: string;
  deadline?: string;
  ressourceIds?: number[];
}
