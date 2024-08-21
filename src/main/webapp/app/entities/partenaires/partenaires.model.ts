export interface IPartenaires {
  id: number;
  nom?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  description?: string | null;
  lien?: string | null;
}

export type NewPartenaires = Omit<IPartenaires, 'id'> & { id: null };
