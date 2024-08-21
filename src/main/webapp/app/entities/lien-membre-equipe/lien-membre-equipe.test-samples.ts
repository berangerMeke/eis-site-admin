import { ILienMembreEquipe, NewLienMembreEquipe } from './lien-membre-equipe.model';

export const sampleWithRequiredData: ILienMembreEquipe = {
  id: 48581,
};

export const sampleWithPartialData: ILienMembreEquipe = {
  id: 13146,
  facebook: 'compressing',
};

export const sampleWithFullData: ILienMembreEquipe = {
  id: 32352,
  facebook: 'instruction eyeballs indigo',
  whatsapp: 'multi-byte Buckinghamshire',
  linkedin: 'Bourgogne Tugrik Games',
  twitter: 'maximize calculate Rubber',
};

export const sampleWithNewData: NewLienMembreEquipe = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
