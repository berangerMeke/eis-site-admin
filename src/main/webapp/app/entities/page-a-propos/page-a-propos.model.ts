import { IMembreEquipe } from 'app/entities/membre-equipe/membre-equipe.model';

export interface IPageAPropos {
  id: number;
  sec1Img1?: string | null;
  sec1Img1ContentType?: string | null;
  sec1Desc1?: string | null;
  sec1Logo?: string | null;
  sec1LogoContentType?: string | null;
  sec1Img2?: string | null;
  sec1Img2ContentType?: string | null;
  sec1Desc2?: string | null;
  sec1Img3?: string | null;
  sec1Img3ContentType?: string | null;
  sec1Desc3?: string | null;
  sec2Titre?: string | null;
  sec2Img?: string | null;
  sec2ImgContentType?: string | null;
  sec2SousTitre?: string | null;
  sec2Text?: string | null;
  sec3Titre?: string | null;
  sec3Img?: string | null;
  sec3ImgContentType?: string | null;
  sec3SousTitre?: string | null;
  sec3Text?: string | null;
  sec4Img?: string | null;
  sec4ImgContentType?: string | null;
  sec4Desc1?: string | null;
  sec4Desc2?: string | null;
  sec4Desc3?: string | null;
  sec4Desc4?: string | null;
  sec5Titre?: string | null;
  membreEquipe1?: Pick<IMembreEquipe, 'id'> | null;
}

export type NewPageAPropos = Omit<IPageAPropos, 'id'> & { id: null };
