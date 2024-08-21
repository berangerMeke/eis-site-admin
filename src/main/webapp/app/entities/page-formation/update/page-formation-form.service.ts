import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPageFormation, NewPageFormation } from '../page-formation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPageFormation for edit and NewPageFormationFormGroupInput for create.
 */
type PageFormationFormGroupInput = IPageFormation | PartialWithRequiredKeyOf<NewPageFormation>;

type PageFormationFormDefaults = Pick<NewPageFormation, 'id'>;

type PageFormationFormGroupContent = {
  id: FormControl<IPageFormation['id'] | NewPageFormation['id']>;
  titre: FormControl<IPageFormation['titre']>;
  sousTitre: FormControl<IPageFormation['sousTitre']>;
};

export type PageFormationFormGroup = FormGroup<PageFormationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PageFormationFormService {
  createPageFormationFormGroup(pageFormation: PageFormationFormGroupInput = { id: null }): PageFormationFormGroup {
    const pageFormationRawValue = {
      ...this.getFormDefaults(),
      ...pageFormation,
    };
    return new FormGroup<PageFormationFormGroupContent>({
      id: new FormControl(
        { value: pageFormationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titre: new FormControl(pageFormationRawValue.titre),
      sousTitre: new FormControl(pageFormationRawValue.sousTitre),
    });
  }

  getPageFormation(form: PageFormationFormGroup): IPageFormation | NewPageFormation {
    return form.getRawValue() as IPageFormation | NewPageFormation;
  }

  resetForm(form: PageFormationFormGroup, pageFormation: PageFormationFormGroupInput): void {
    const pageFormationRawValue = { ...this.getFormDefaults(), ...pageFormation };
    form.reset(
      {
        ...pageFormationRawValue,
        id: { value: pageFormationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PageFormationFormDefaults {
    return {
      id: null,
    };
  }
}
