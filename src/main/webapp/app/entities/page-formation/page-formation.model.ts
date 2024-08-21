export interface IPageFormation {
  id: number;
  titre?: string | null;
  sousTitre?: string | null;
}

export type NewPageFormation = Omit<IPageFormation, 'id'> & { id: null };
