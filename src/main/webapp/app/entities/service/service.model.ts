export interface IService {
  id: number;
  nom?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  description?: string | null;
  lien?: string | null;
}

export type NewService = Omit<IService, 'id'> & { id: null };
