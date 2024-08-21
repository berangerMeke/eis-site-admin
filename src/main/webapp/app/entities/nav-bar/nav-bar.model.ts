export interface INavBar {
  id: number;
  logo?: string | null;
  logoContentType?: string | null;
  menu1?: string | null;
  menu2?: string | null;
  menu3?: string | null;
  menu4?: string | null;
  menu5?: string | null;
  menu6?: string | null;
}

export type NewNavBar = Omit<INavBar, 'id'> & { id: null };
