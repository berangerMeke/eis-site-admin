import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 12311,
};

export const sampleWithPartialData: IService = {
  id: 27136,
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'needs-based',
};

export const sampleWithFullData: IService = {
  id: 11829,
  nom: 'index Handmade',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'parse Keyboard orange',
  lien: 'interface',
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
