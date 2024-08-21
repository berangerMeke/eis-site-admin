import { IPageService, NewPageService } from './page-service.model';

export const sampleWithRequiredData: IPageService = {
  id: 74707,
};

export const sampleWithPartialData: IPageService = {
  id: 88344,
};

export const sampleWithFullData: IPageService = {
  id: 83174,
  titre: 'Jamaican',
  soustitre: 'Auvergne',
};

export const sampleWithNewData: NewPageService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
