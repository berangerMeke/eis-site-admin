import { IPageFormation, NewPageFormation } from './page-formation.model';

export const sampleWithRequiredData: IPageFormation = {
  id: 92654,
};

export const sampleWithPartialData: IPageFormation = {
  id: 84329,
};

export const sampleWithFullData: IPageFormation = {
  id: 85822,
  titre: 'mobile azure',
  sousTitre: 'Small',
};

export const sampleWithNewData: NewPageFormation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
