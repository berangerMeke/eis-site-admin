import { IPartenaires, NewPartenaires } from './partenaires.model';

export const sampleWithRequiredData: IPartenaires = {
  id: 69403,
};

export const sampleWithPartialData: IPartenaires = {
  id: 63092,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  lien: 'system',
};

export const sampleWithFullData: IPartenaires = {
  id: 50789,
  nom: 'Cheese task-force experiences',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'intuitive',
  lien: 'Cambridgeshire',
};

export const sampleWithNewData: NewPartenaires = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
