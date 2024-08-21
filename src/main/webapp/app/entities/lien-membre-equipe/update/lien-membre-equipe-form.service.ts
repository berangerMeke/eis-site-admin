import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILienMembreEquipe, NewLienMembreEquipe } from '../lien-membre-equipe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILienMembreEquipe for edit and NewLienMembreEquipeFormGroupInput for create.
 */
type LienMembreEquipeFormGroupInput = ILienMembreEquipe | PartialWithRequiredKeyOf<NewLienMembreEquipe>;

type LienMembreEquipeFormDefaults = Pick<NewLienMembreEquipe, 'id'>;

type LienMembreEquipeFormGroupContent = {
  id: FormControl<ILienMembreEquipe['id'] | NewLienMembreEquipe['id']>;
  facebook: FormControl<ILienMembreEquipe['facebook']>;
  whatsapp: FormControl<ILienMembreEquipe['whatsapp']>;
  linkedin: FormControl<ILienMembreEquipe['linkedin']>;
  twitter: FormControl<ILienMembreEquipe['twitter']>;
};

export type LienMembreEquipeFormGroup = FormGroup<LienMembreEquipeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LienMembreEquipeFormService {
  createLienMembreEquipeFormGroup(lienMembreEquipe: LienMembreEquipeFormGroupInput = { id: null }): LienMembreEquipeFormGroup {
    const lienMembreEquipeRawValue = {
      ...this.getFormDefaults(),
      ...lienMembreEquipe,
    };
    return new FormGroup<LienMembreEquipeFormGroupContent>({
      id: new FormControl(
        { value: lienMembreEquipeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      facebook: new FormControl(lienMembreEquipeRawValue.facebook),
      whatsapp: new FormControl(lienMembreEquipeRawValue.whatsapp),
      linkedin: new FormControl(lienMembreEquipeRawValue.linkedin),
      twitter: new FormControl(lienMembreEquipeRawValue.twitter),
    });
  }

  getLienMembreEquipe(form: LienMembreEquipeFormGroup): ILienMembreEquipe | NewLienMembreEquipe {
    return form.getRawValue() as ILienMembreEquipe | NewLienMembreEquipe;
  }

  resetForm(form: LienMembreEquipeFormGroup, lienMembreEquipe: LienMembreEquipeFormGroupInput): void {
    const lienMembreEquipeRawValue = { ...this.getFormDefaults(), ...lienMembreEquipe };
    form.reset(
      {
        ...lienMembreEquipeRawValue,
        id: { value: lienMembreEquipeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LienMembreEquipeFormDefaults {
    return {
      id: null,
    };
  }
}
