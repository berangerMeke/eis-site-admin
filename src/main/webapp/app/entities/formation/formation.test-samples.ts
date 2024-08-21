import { IFormation, NewFormation } from './formation.model';

export const sampleWithRequiredData: IFormation = {
  id: 7564,
};

export const sampleWithPartialData: IFormation = {
  id: 69269,
  nom: 'up transparent withdrawal',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  texte: "l'Odéon Home Joubert",
  duree: 78851,
};

export const sampleWithFullData: IFormation = {
  id: 33790,
  nom: 'Persevering',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  texte: 'ADP RSS',
  cout: 43016,
  duree: 14118,
  categorie: 'Assistant',
  lien: "web-enabled incremental d'Azur",
};

export const sampleWithNewData: NewFormation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
