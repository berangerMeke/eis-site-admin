import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPageAccueil, NewPageAccueil } from '../page-accueil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPageAccueil for edit and NewPageAccueilFormGroupInput for create.
 */
type PageAccueilFormGroupInput = IPageAccueil | PartialWithRequiredKeyOf<NewPageAccueil>;

type PageAccueilFormDefaults = Pick<NewPageAccueil, 'id'>;

type PageAccueilFormGroupContent = {
  id: FormControl<IPageAccueil['id'] | NewPageAccueil['id']>;
  sec1Titre: FormControl<IPageAccueil['sec1Titre']>;
  sec1Texte: FormControl<IPageAccueil['sec1Texte']>;
  secImage: FormControl<IPageAccueil['secImage']>;
  secImageContentType: FormControl<IPageAccueil['secImageContentType']>;
  sec1Bouton: FormControl<IPageAccueil['sec1Bouton']>;
  sec2Titre: FormControl<IPageAccueil['sec2Titre']>;
  sec2Text: FormControl<IPageAccueil['sec2Text']>;
  sec2Bouton: FormControl<IPageAccueil['sec2Bouton']>;
  sec3Titre: FormControl<IPageAccueil['sec3Titre']>;
  sec3Blog1Img: FormControl<IPageAccueil['sec3Blog1Img']>;
  sec3Blog1ImgContentType: FormControl<IPageAccueil['sec3Blog1ImgContentType']>;
  sec3Blog2Img: FormControl<IPageAccueil['sec3Blog2Img']>;
  sec3Blog2ImgContentType: FormControl<IPageAccueil['sec3Blog2ImgContentType']>;
  sec3Blog3Img: FormControl<IPageAccueil['sec3Blog3Img']>;
  sec3Blog3ImgContentType: FormControl<IPageAccueil['sec3Blog3ImgContentType']>;
  sec3Blog1SousTitre: FormControl<IPageAccueil['sec3Blog1SousTitre']>;
  sec3Blog2SousTitre: FormControl<IPageAccueil['sec3Blog2SousTitre']>;
  sec3Blog3SousTitre: FormControl<IPageAccueil['sec3Blog3SousTitre']>;
  sec3Blog1Text: FormControl<IPageAccueil['sec3Blog1Text']>;
  sec3Blog2Text: FormControl<IPageAccueil['sec3Blog2Text']>;
  sec3Blog3Text: FormControl<IPageAccueil['sec3Blog3Text']>;
  sec4Titre: FormControl<IPageAccueil['sec4Titre']>;
  sec4Text: FormControl<IPageAccueil['sec4Text']>;
  sec4Blog1Img: FormControl<IPageAccueil['sec4Blog1Img']>;
  sec4Blog1ImgContentType: FormControl<IPageAccueil['sec4Blog1ImgContentType']>;
  sec4Blog1SousTitre: FormControl<IPageAccueil['sec4Blog1SousTitre']>;
  sec4Blog2SousTitre: FormControl<IPageAccueil['sec4Blog2SousTitre']>;
  sec4Blog1Text: FormControl<IPageAccueil['sec4Blog1Text']>;
  sec4Blog2Text: FormControl<IPageAccueil['sec4Blog2Text']>;
  sec5Titre: FormControl<IPageAccueil['sec5Titre']>;
  sec5Blog1Img: FormControl<IPageAccueil['sec5Blog1Img']>;
  sec5Blog1ImgContentType: FormControl<IPageAccueil['sec5Blog1ImgContentType']>;
  sec5Blog2Img: FormControl<IPageAccueil['sec5Blog2Img']>;
  sec5Blog2ImgContentType: FormControl<IPageAccueil['sec5Blog2ImgContentType']>;
  sec5Blog3Img: FormControl<IPageAccueil['sec5Blog3Img']>;
  sec5Blog3ImgContentType: FormControl<IPageAccueil['sec5Blog3ImgContentType']>;
  sec5Blog4Img: FormControl<IPageAccueil['sec5Blog4Img']>;
  sec5Blog4ImgContentType: FormControl<IPageAccueil['sec5Blog4ImgContentType']>;
  sec5Blog5Img: FormControl<IPageAccueil['sec5Blog5Img']>;
  sec5Blog5ImgContentType: FormControl<IPageAccueil['sec5Blog5ImgContentType']>;
  sec5Blog6Img: FormControl<IPageAccueil['sec5Blog6Img']>;
  sec5Blog6ImgContentType: FormControl<IPageAccueil['sec5Blog6ImgContentType']>;
  sec5Blog7Img: FormControl<IPageAccueil['sec5Blog7Img']>;
  sec5Blog7ImgContentType: FormControl<IPageAccueil['sec5Blog7ImgContentType']>;
  sec5Blog1SousTitre: FormControl<IPageAccueil['sec5Blog1SousTitre']>;
  sec5Blog1SousTitreContentType: FormControl<IPageAccueil['sec5Blog1SousTitreContentType']>;
  sec5Blog2SousTitre: FormControl<IPageAccueil['sec5Blog2SousTitre']>;
  sec5Blog2SousTitreContentType: FormControl<IPageAccueil['sec5Blog2SousTitreContentType']>;
  sec5Blog3SousTitre: FormControl<IPageAccueil['sec5Blog3SousTitre']>;
  sec5Blog3SousTitreContentType: FormControl<IPageAccueil['sec5Blog3SousTitreContentType']>;
  sec5Blog4SousTitre: FormControl<IPageAccueil['sec5Blog4SousTitre']>;
  sec5Blog4SousTitreContentType: FormControl<IPageAccueil['sec5Blog4SousTitreContentType']>;
  sec5Blog5SousTitre: FormControl<IPageAccueil['sec5Blog5SousTitre']>;
  sec5Blog5SousTitreContentType: FormControl<IPageAccueil['sec5Blog5SousTitreContentType']>;
  sec5Blog6SousTitre: FormControl<IPageAccueil['sec5Blog6SousTitre']>;
  sec5Blog6SousTitreContentType: FormControl<IPageAccueil['sec5Blog6SousTitreContentType']>;
  sec5Blog7SousTitre: FormControl<IPageAccueil['sec5Blog7SousTitre']>;
  sec5Blog7SousTitreContentType: FormControl<IPageAccueil['sec5Blog7SousTitreContentType']>;
  sec5Blog1Text: FormControl<IPageAccueil['sec5Blog1Text']>;
  sec5Blog2Text: FormControl<IPageAccueil['sec5Blog2Text']>;
  sec5Blog3Text: FormControl<IPageAccueil['sec5Blog3Text']>;
  sec5Blog4Text: FormControl<IPageAccueil['sec5Blog4Text']>;
  sec5Blog5Text: FormControl<IPageAccueil['sec5Blog5Text']>;
  sec5Blog6Text: FormControl<IPageAccueil['sec5Blog6Text']>;
  sec5Blog7Text: FormControl<IPageAccueil['sec5Blog7Text']>;
  sec6Titre: FormControl<IPageAccueil['sec6Titre']>;
  sec6Img: FormControl<IPageAccueil['sec6Img']>;
  sec6ImgContentType: FormControl<IPageAccueil['sec6ImgContentType']>;
  sec6Text: FormControl<IPageAccueil['sec6Text']>;
  sec7Titre: FormControl<IPageAccueil['sec7Titre']>;
  sec7Img: FormControl<IPageAccueil['sec7Img']>;
  sec7ImgContentType: FormControl<IPageAccueil['sec7ImgContentType']>;
  sec7Text: FormControl<IPageAccueil['sec7Text']>;
  sec8Titre: FormControl<IPageAccueil['sec8Titre']>;
  sec8Img: FormControl<IPageAccueil['sec8Img']>;
  sec8ImgContentType: FormControl<IPageAccueil['sec8ImgContentType']>;
  sec8Text: FormControl<IPageAccueil['sec8Text']>;
  sec9Titre: FormControl<IPageAccueil['sec9Titre']>;
  sec9Img: FormControl<IPageAccueil['sec9Img']>;
  sec9ImgContentType: FormControl<IPageAccueil['sec9ImgContentType']>;
  sec9Text: FormControl<IPageAccueil['sec9Text']>;
  partenaires1: FormControl<IPageAccueil['partenaires1']>;
};

export type PageAccueilFormGroup = FormGroup<PageAccueilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PageAccueilFormService {
  createPageAccueilFormGroup(pageAccueil: PageAccueilFormGroupInput = { id: null }): PageAccueilFormGroup {
    const pageAccueilRawValue = {
      ...this.getFormDefaults(),
      ...pageAccueil,
    };
    return new FormGroup<PageAccueilFormGroupContent>({
      id: new FormControl(
        { value: pageAccueilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sec1Titre: new FormControl(pageAccueilRawValue.sec1Titre),
      sec1Texte: new FormControl(pageAccueilRawValue.sec1Texte),
      secImage: new FormControl(pageAccueilRawValue.secImage),
      secImageContentType: new FormControl(pageAccueilRawValue.secImageContentType),
      sec1Bouton: new FormControl(pageAccueilRawValue.sec1Bouton),
      sec2Titre: new FormControl(pageAccueilRawValue.sec2Titre),
      sec2Text: new FormControl(pageAccueilRawValue.sec2Text),
      sec2Bouton: new FormControl(pageAccueilRawValue.sec2Bouton),
      sec3Titre: new FormControl(pageAccueilRawValue.sec3Titre),
      sec3Blog1Img: new FormControl(pageAccueilRawValue.sec3Blog1Img),
      sec3Blog1ImgContentType: new FormControl(pageAccueilRawValue.sec3Blog1ImgContentType),
      sec3Blog2Img: new FormControl(pageAccueilRawValue.sec3Blog2Img),
      sec3Blog2ImgContentType: new FormControl(pageAccueilRawValue.sec3Blog2ImgContentType),
      sec3Blog3Img: new FormControl(pageAccueilRawValue.sec3Blog3Img),
      sec3Blog3ImgContentType: new FormControl(pageAccueilRawValue.sec3Blog3ImgContentType),
      sec3Blog1SousTitre: new FormControl(pageAccueilRawValue.sec3Blog1SousTitre),
      sec3Blog2SousTitre: new FormControl(pageAccueilRawValue.sec3Blog2SousTitre),
      sec3Blog3SousTitre: new FormControl(pageAccueilRawValue.sec3Blog3SousTitre),
      sec3Blog1Text: new FormControl(pageAccueilRawValue.sec3Blog1Text),
      sec3Blog2Text: new FormControl(pageAccueilRawValue.sec3Blog2Text),
      sec3Blog3Text: new FormControl(pageAccueilRawValue.sec3Blog3Text),
      sec4Titre: new FormControl(pageAccueilRawValue.sec4Titre),
      sec4Text: new FormControl(pageAccueilRawValue.sec4Text),
      sec4Blog1Img: new FormControl(pageAccueilRawValue.sec4Blog1Img),
      sec4Blog1ImgContentType: new FormControl(pageAccueilRawValue.sec4Blog1ImgContentType),
      sec4Blog1SousTitre: new FormControl(pageAccueilRawValue.sec4Blog1SousTitre),
      sec4Blog2SousTitre: new FormControl(pageAccueilRawValue.sec4Blog2SousTitre),
      sec4Blog1Text: new FormControl(pageAccueilRawValue.sec4Blog1Text),
      sec4Blog2Text: new FormControl(pageAccueilRawValue.sec4Blog2Text),
      sec5Titre: new FormControl(pageAccueilRawValue.sec5Titre),
      sec5Blog1Img: new FormControl(pageAccueilRawValue.sec5Blog1Img),
      sec5Blog1ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog1ImgContentType),
      sec5Blog2Img: new FormControl(pageAccueilRawValue.sec5Blog2Img),
      sec5Blog2ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog2ImgContentType),
      sec5Blog3Img: new FormControl(pageAccueilRawValue.sec5Blog3Img),
      sec5Blog3ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog3ImgContentType),
      sec5Blog4Img: new FormControl(pageAccueilRawValue.sec5Blog4Img),
      sec5Blog4ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog4ImgContentType),
      sec5Blog5Img: new FormControl(pageAccueilRawValue.sec5Blog5Img),
      sec5Blog5ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog5ImgContentType),
      sec5Blog6Img: new FormControl(pageAccueilRawValue.sec5Blog6Img),
      sec5Blog6ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog6ImgContentType),
      sec5Blog7Img: new FormControl(pageAccueilRawValue.sec5Blog7Img),
      sec5Blog7ImgContentType: new FormControl(pageAccueilRawValue.sec5Blog7ImgContentType),
      sec5Blog1SousTitre: new FormControl(pageAccueilRawValue.sec5Blog1SousTitre),
      sec5Blog1SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog1SousTitreContentType),
      sec5Blog2SousTitre: new FormControl(pageAccueilRawValue.sec5Blog2SousTitre),
      sec5Blog2SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog2SousTitreContentType),
      sec5Blog3SousTitre: new FormControl(pageAccueilRawValue.sec5Blog3SousTitre),
      sec5Blog3SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog3SousTitreContentType),
      sec5Blog4SousTitre: new FormControl(pageAccueilRawValue.sec5Blog4SousTitre),
      sec5Blog4SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog4SousTitreContentType),
      sec5Blog5SousTitre: new FormControl(pageAccueilRawValue.sec5Blog5SousTitre),
      sec5Blog5SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog5SousTitreContentType),
      sec5Blog6SousTitre: new FormControl(pageAccueilRawValue.sec5Blog6SousTitre),
      sec5Blog6SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog6SousTitreContentType),
      sec5Blog7SousTitre: new FormControl(pageAccueilRawValue.sec5Blog7SousTitre),
      sec5Blog7SousTitreContentType: new FormControl(pageAccueilRawValue.sec5Blog7SousTitreContentType),
      sec5Blog1Text: new FormControl(pageAccueilRawValue.sec5Blog1Text),
      sec5Blog2Text: new FormControl(pageAccueilRawValue.sec5Blog2Text),
      sec5Blog3Text: new FormControl(pageAccueilRawValue.sec5Blog3Text),
      sec5Blog4Text: new FormControl(pageAccueilRawValue.sec5Blog4Text),
      sec5Blog5Text: new FormControl(pageAccueilRawValue.sec5Blog5Text),
      sec5Blog6Text: new FormControl(pageAccueilRawValue.sec5Blog6Text),
      sec5Blog7Text: new FormControl(pageAccueilRawValue.sec5Blog7Text),
      sec6Titre: new FormControl(pageAccueilRawValue.sec6Titre),
      sec6Img: new FormControl(pageAccueilRawValue.sec6Img),
      sec6ImgContentType: new FormControl(pageAccueilRawValue.sec6ImgContentType),
      sec6Text: new FormControl(pageAccueilRawValue.sec6Text),
      sec7Titre: new FormControl(pageAccueilRawValue.sec7Titre),
      sec7Img: new FormControl(pageAccueilRawValue.sec7Img),
      sec7ImgContentType: new FormControl(pageAccueilRawValue.sec7ImgContentType),
      sec7Text: new FormControl(pageAccueilRawValue.sec7Text),
      sec8Titre: new FormControl(pageAccueilRawValue.sec8Titre),
      sec8Img: new FormControl(pageAccueilRawValue.sec8Img),
      sec8ImgContentType: new FormControl(pageAccueilRawValue.sec8ImgContentType),
      sec8Text: new FormControl(pageAccueilRawValue.sec8Text),
      sec9Titre: new FormControl(pageAccueilRawValue.sec9Titre),
      sec9Img: new FormControl(pageAccueilRawValue.sec9Img),
      sec9ImgContentType: new FormControl(pageAccueilRawValue.sec9ImgContentType),
      sec9Text: new FormControl(pageAccueilRawValue.sec9Text),
      partenaires1: new FormControl(pageAccueilRawValue.partenaires1),
    });
  }

  getPageAccueil(form: PageAccueilFormGroup): IPageAccueil | NewPageAccueil {
    return form.getRawValue() as IPageAccueil | NewPageAccueil;
  }

  resetForm(form: PageAccueilFormGroup, pageAccueil: PageAccueilFormGroupInput): void {
    const pageAccueilRawValue = { ...this.getFormDefaults(), ...pageAccueil };
    form.reset(
      {
        ...pageAccueilRawValue,
        id: { value: pageAccueilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PageAccueilFormDefaults {
    return {
      id: null,
    };
  }
}
