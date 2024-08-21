import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPageAPropos, NewPageAPropos } from '../page-a-propos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPageAPropos for edit and NewPageAProposFormGroupInput for create.
 */
type PageAProposFormGroupInput = IPageAPropos | PartialWithRequiredKeyOf<NewPageAPropos>;

type PageAProposFormDefaults = Pick<NewPageAPropos, 'id'>;

type PageAProposFormGroupContent = {
  id: FormControl<IPageAPropos['id'] | NewPageAPropos['id']>;
  sec1Img1: FormControl<IPageAPropos['sec1Img1']>;
  sec1Img1ContentType: FormControl<IPageAPropos['sec1Img1ContentType']>;
  sec1Desc1: FormControl<IPageAPropos['sec1Desc1']>;
  sec1Logo: FormControl<IPageAPropos['sec1Logo']>;
  sec1LogoContentType: FormControl<IPageAPropos['sec1LogoContentType']>;
  sec1Img2: FormControl<IPageAPropos['sec1Img2']>;
  sec1Img2ContentType: FormControl<IPageAPropos['sec1Img2ContentType']>;
  sec1Desc2: FormControl<IPageAPropos['sec1Desc2']>;
  sec1Img3: FormControl<IPageAPropos['sec1Img3']>;
  sec1Img3ContentType: FormControl<IPageAPropos['sec1Img3ContentType']>;
  sec1Desc3: FormControl<IPageAPropos['sec1Desc3']>;
  sec2Titre: FormControl<IPageAPropos['sec2Titre']>;
  sec2Img: FormControl<IPageAPropos['sec2Img']>;
  sec2ImgContentType: FormControl<IPageAPropos['sec2ImgContentType']>;
  sec2SousTitre: FormControl<IPageAPropos['sec2SousTitre']>;
  sec2Text: FormControl<IPageAPropos['sec2Text']>;
  sec3Titre: FormControl<IPageAPropos['sec3Titre']>;
  sec3Img: FormControl<IPageAPropos['sec3Img']>;
  sec3ImgContentType: FormControl<IPageAPropos['sec3ImgContentType']>;
  sec3SousTitre: FormControl<IPageAPropos['sec3SousTitre']>;
  sec3Text: FormControl<IPageAPropos['sec3Text']>;
  sec4Img: FormControl<IPageAPropos['sec4Img']>;
  sec4ImgContentType: FormControl<IPageAPropos['sec4ImgContentType']>;
  sec4Desc1: FormControl<IPageAPropos['sec4Desc1']>;
  sec4Desc2: FormControl<IPageAPropos['sec4Desc2']>;
  sec4Desc3: FormControl<IPageAPropos['sec4Desc3']>;
  sec4Desc4: FormControl<IPageAPropos['sec4Desc4']>;
  sec5Titre: FormControl<IPageAPropos['sec5Titre']>;
  membreEquipe1: FormControl<IPageAPropos['membreEquipe1']>;
};

export type PageAProposFormGroup = FormGroup<PageAProposFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PageAProposFormService {
  createPageAProposFormGroup(pageAPropos: PageAProposFormGroupInput = { id: null }): PageAProposFormGroup {
    const pageAProposRawValue = {
      ...this.getFormDefaults(),
      ...pageAPropos,
    };
    return new FormGroup<PageAProposFormGroupContent>({
      id: new FormControl(
        { value: pageAProposRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sec1Img1: new FormControl(pageAProposRawValue.sec1Img1),
      sec1Img1ContentType: new FormControl(pageAProposRawValue.sec1Img1ContentType),
      sec1Desc1: new FormControl(pageAProposRawValue.sec1Desc1),
      sec1Logo: new FormControl(pageAProposRawValue.sec1Logo),
      sec1LogoContentType: new FormControl(pageAProposRawValue.sec1LogoContentType),
      sec1Img2: new FormControl(pageAProposRawValue.sec1Img2),
      sec1Img2ContentType: new FormControl(pageAProposRawValue.sec1Img2ContentType),
      sec1Desc2: new FormControl(pageAProposRawValue.sec1Desc2),
      sec1Img3: new FormControl(pageAProposRawValue.sec1Img3),
      sec1Img3ContentType: new FormControl(pageAProposRawValue.sec1Img3ContentType),
      sec1Desc3: new FormControl(pageAProposRawValue.sec1Desc3),
      sec2Titre: new FormControl(pageAProposRawValue.sec2Titre),
      sec2Img: new FormControl(pageAProposRawValue.sec2Img),
      sec2ImgContentType: new FormControl(pageAProposRawValue.sec2ImgContentType),
      sec2SousTitre: new FormControl(pageAProposRawValue.sec2SousTitre),
      sec2Text: new FormControl(pageAProposRawValue.sec2Text),
      sec3Titre: new FormControl(pageAProposRawValue.sec3Titre),
      sec3Img: new FormControl(pageAProposRawValue.sec3Img),
      sec3ImgContentType: new FormControl(pageAProposRawValue.sec3ImgContentType),
      sec3SousTitre: new FormControl(pageAProposRawValue.sec3SousTitre),
      sec3Text: new FormControl(pageAProposRawValue.sec3Text),
      sec4Img: new FormControl(pageAProposRawValue.sec4Img),
      sec4ImgContentType: new FormControl(pageAProposRawValue.sec4ImgContentType),
      sec4Desc1: new FormControl(pageAProposRawValue.sec4Desc1),
      sec4Desc2: new FormControl(pageAProposRawValue.sec4Desc2),
      sec4Desc3: new FormControl(pageAProposRawValue.sec4Desc3),
      sec4Desc4: new FormControl(pageAProposRawValue.sec4Desc4),
      sec5Titre: new FormControl(pageAProposRawValue.sec5Titre),
      membreEquipe1: new FormControl(pageAProposRawValue.membreEquipe1),
    });
  }

  getPageAPropos(form: PageAProposFormGroup): IPageAPropos | NewPageAPropos {
    return form.getRawValue() as IPageAPropos | NewPageAPropos;
  }

  resetForm(form: PageAProposFormGroup, pageAPropos: PageAProposFormGroupInput): void {
    const pageAProposRawValue = { ...this.getFormDefaults(), ...pageAPropos };
    form.reset(
      {
        ...pageAProposRawValue,
        id: { value: pageAProposRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PageAProposFormDefaults {
    return {
      id: null,
    };
  }
}
