export interface IPageEquipe {
  id: number;
  sec1Img?: string | null;
  sec1Titre?: string | null;
  sec1Desc?: string | null;
}

export type NewPageEquipe = Omit<IPageEquipe, 'id'> & { id: null };
