export interface IPageService {
  id: number;
  titre?: string | null;
  soustitre?: string | null;
}

export type NewPageService = Omit<IPageService, 'id'> & { id: null };
