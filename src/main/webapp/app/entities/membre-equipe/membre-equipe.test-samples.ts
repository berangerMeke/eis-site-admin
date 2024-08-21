import { IMembreEquipe, NewMembreEquipe } from './membre-equipe.model';

export const sampleWithRequiredData: IMembreEquipe = {
  id: 30766,
};

export const sampleWithPartialData: IMembreEquipe = {
  id: 55542,
  prenom: 'microchip',
  diplome: 'a payment management',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'b',
  message: 'Islands XSS Producteur',
  fonction: 'white invoice',
};

export const sampleWithFullData: IMembreEquipe = {
  id: 19537,
  nom: 'Devolved Tasty',
  prenom: 'bricks-and-clicks copying magenta',
  telephone: '+33 303980973',
  adresseMail: 'Mauritanie Saint-Bernard',
  certification: 'ADP',
  diplome: 'Rubber',
  niveauEtude: 'Steel connecting',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'Agent',
  message: 'a bypassing',
  fonction: 'Phased',
};

export const sampleWithNewData: NewMembreEquipe = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
