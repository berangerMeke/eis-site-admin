export interface IContact {
  id: number;
  telephone?: string | null;
  email?: string | null;
  boitePostale?: string | null;
  localisation?: string | null;
}

export type NewContact = Omit<IContact, 'id'> & { id: null };
