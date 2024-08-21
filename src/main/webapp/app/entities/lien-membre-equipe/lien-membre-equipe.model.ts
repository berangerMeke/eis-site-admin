export interface ILienMembreEquipe {
  id: number;
  facebook?: string | null;
  whatsapp?: string | null;
  linkedin?: string | null;
  twitter?: string | null;
}

export type NewLienMembreEquipe = Omit<ILienMembreEquipe, 'id'> & { id: null };
