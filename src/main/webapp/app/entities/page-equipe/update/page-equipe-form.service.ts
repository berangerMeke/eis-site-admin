import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPageEquipe, NewPageEquipe } from '../page-equipe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPageEquipe for edit and NewPageEquipeFormGroupInput for create.
 */
type PageEquipeFormGroupInput = IPageEquipe | PartialWithRequiredKeyOf<NewPageEquipe>;

type PageEquipeFormDefaults = Pick<NewPageEquipe, 'id'>;

type PageEquipeFormGroupContent = {
  id: FormControl<IPageEquipe['id'] | NewPageEquipe['id']>;
  sec1Img: FormControl<IPageEquipe['sec1Img']>;
  sec1Titre: FormControl<IPageEquipe['sec1Titre']>;
  sec1Desc: FormControl<IPageEquipe['sec1Desc']>;
};

export type PageEquipeFormGroup = FormGroup<PageEquipeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PageEquipeFormService {
  createPageEquipeFormGroup(pageEquipe: PageEquipeFormGroupInput = { id: null }): PageEquipeFormGroup {
    const pageEquipeRawValue = {
      ...this.getFormDefaults(),
      ...pageEquipe,
    };
    return new FormGroup<PageEquipeFormGroupContent>({
      id: new FormControl(
        { value: pageEquipeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sec1Img: new FormControl(pageEquipeRawValue.sec1Img),
      sec1Titre: new FormControl(pageEquipeRawValue.sec1Titre),
      sec1Desc: new FormControl(pageEquipeRawValue.sec1Desc),
    });
  }

  getPageEquipe(form: PageEquipeFormGroup): IPageEquipe | NewPageEquipe {
    return form.getRawValue() as IPageEquipe | NewPageEquipe;
  }

  resetForm(form: PageEquipeFormGroup, pageEquipe: PageEquipeFormGroupInput): void {
    const pageEquipeRawValue = { ...this.getFormDefaults(), ...pageEquipe };
    form.reset(
      {
        ...pageEquipeRawValue,
        id: { value: pageEquipeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PageEquipeFormDefaults {
    return {
      id: null,
    };
  }
}
