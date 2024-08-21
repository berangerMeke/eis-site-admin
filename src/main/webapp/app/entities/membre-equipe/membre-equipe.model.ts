import { IPageEquipe } from 'app/entities/page-equipe/page-equipe.model';

export interface IMembreEquipe {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  telephone?: string | null;
  adresseMail?: string | null;
  certification?: string | null;
  diplome?: string | null;
  niveauEtude?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  description?: string | null;
  message?: string | null;
  fonction?: string | null;
  pageEquipe1?: Pick<IPageEquipe, 'id'> | null;
}

export type NewMembreEquipe = Omit<IMembreEquipe, 'id'> & { id: null };
