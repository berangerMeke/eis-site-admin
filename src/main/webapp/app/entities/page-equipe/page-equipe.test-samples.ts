import { IPageEquipe, NewPageEquipe } from './page-equipe.model';

export const sampleWithRequiredData: IPageEquipe = {
  id: 94042,
};

export const sampleWithPartialData: IPageEquipe = {
  id: 24513,
  sec1Img: 'purple c multi-byte',
};

export const sampleWithFullData: IPageEquipe = {
  id: 21883,
  sec1Img: 'Shoes 24/7 bypass',
  sec1Titre: 'compress 1080p Specialiste',
  sec1Desc: 'du Ingenieur Plastic',
};

export const sampleWithNewData: NewPageEquipe = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
