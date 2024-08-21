import { IContact, NewContact } from './contact.model';

export const sampleWithRequiredData: IContact = {
  id: 36482,
};

export const sampleWithPartialData: IContact = {
  id: 25447,
  email: 'Guillaume_Cousin96@gmail.com',
};

export const sampleWithFullData: IContact = {
  id: 52265,
  telephone: '+33 508033662',
  email: 'meric.Gonzalez@yahoo.fr',
  boitePostale: 'Beauty Berkshire Specialiste',
  localisation: 'a capacitor',
};

export const sampleWithNewData: NewContact = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
