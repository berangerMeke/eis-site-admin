export interface IFormation {
  id: number;
  nom?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  texte?: string | null;
  cout?: number | null;
  duree?: number | null;
  categorie?: string | null;
  lien?: string | null;
}

export type NewFormation = Omit<IFormation, 'id'> & { id: null };
