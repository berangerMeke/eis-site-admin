import { IPageAPropos, NewPageAPropos } from './page-a-propos.model';

export const sampleWithRequiredData: IPageAPropos = {
  id: 83184,
};

export const sampleWithPartialData: IPageAPropos = {
  id: 82727,
  sec1Img1: '../fake-data/blob/hipster.png',
  sec1Img1ContentType: 'unknown',
  sec1Desc1: 'paradigm Bosnie-Herzégovine capacitor',
  sec1Logo: '../fake-data/blob/hipster.png',
  sec1LogoContentType: 'unknown',
  sec2SousTitre: 'coherent Licensed Specialiste',
  sec2Text: 'Vaneau Agent drive',
  sec3Img: '../fake-data/blob/hipster.png',
  sec3ImgContentType: 'unknown',
  sec4Desc2: 'Frozen copying',
  sec4Desc3: 'hacking purple Steel',
  sec5Titre: 'bypassing',
};

export const sampleWithFullData: IPageAPropos = {
  id: 56358,
  sec1Img1: '../fake-data/blob/hipster.png',
  sec1Img1ContentType: 'unknown',
  sec1Desc1: 'la Wooden Cambridgeshire',
  sec1Logo: '../fake-data/blob/hipster.png',
  sec1LogoContentType: 'unknown',
  sec1Img2: '../fake-data/blob/hipster.png',
  sec1Img2ContentType: 'unknown',
  sec1Desc2: 'Avon Shirt',
  sec1Img3: '../fake-data/blob/hipster.png',
  sec1Img3ContentType: 'unknown',
  sec1Desc3: 'Ergonomic Account Leu',
  sec2Titre: 'firewall',
  sec2Img: '../fake-data/blob/hipster.png',
  sec2ImgContentType: 'unknown',
  sec2SousTitre: 'seize array',
  sec2Text: 'matrices',
  sec3Titre: 'Gorgeous clicks-and-mortar',
  sec3Img: '../fake-data/blob/hipster.png',
  sec3ImgContentType: 'unknown',
  sec3SousTitre: 'withdrawal',
  sec3Text: 'Shoes c',
  sec4Img: '../fake-data/blob/hipster.png',
  sec4ImgContentType: 'unknown',
  sec4Desc1: 'la',
  sec4Desc2: 'Awesome moratorium plum',
  sec4Desc3: 'compress c Berkshire',
  sec4Desc4: 'disintermediate driver',
  sec5Titre: 'Awesome Persistent',
};

export const sampleWithNewData: NewPageAPropos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
