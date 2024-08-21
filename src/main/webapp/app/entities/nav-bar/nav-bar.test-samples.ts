import { INavBar, NewNavBar } from './nav-bar.model';

export const sampleWithRequiredData: INavBar = {
  id: 36904,
};

export const sampleWithPartialData: INavBar = {
  id: 36422,
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  menu2: 'Home a 24/7',
  menu4: 'Fresh Équateur',
  menu6: 'Plastic Kroon Cambridgeshire',
};

export const sampleWithFullData: INavBar = {
  id: 73780,
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  menu1: 'Manager c Keyboard',
  menu2: 'a Market',
  menu3: 'Account',
  menu4: 'b Monitored infrastructures',
  menu5: 'Pierre',
  menu6: 'intranet',
};

export const sampleWithNewData: NewNavBar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
