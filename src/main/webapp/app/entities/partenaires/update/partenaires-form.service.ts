import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPartenaires, NewPartenaires } from '../partenaires.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPartenaires for edit and NewPartenairesFormGroupInput for create.
 */
type PartenairesFormGroupInput = IPartenaires | PartialWithRequiredKeyOf<NewPartenaires>;

type PartenairesFormDefaults = Pick<NewPartenaires, 'id'>;

type PartenairesFormGroupContent = {
  id: FormControl<IPartenaires['id'] | NewPartenaires['id']>;
  nom: FormControl<IPartenaires['nom']>;
  photo: FormControl<IPartenaires['photo']>;
  photoContentType: FormControl<IPartenaires['photoContentType']>;
  description: FormControl<IPartenaires['description']>;
  lien: FormControl<IPartenaires['lien']>;
};

export type PartenairesFormGroup = FormGroup<PartenairesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PartenairesFormService {
  createPartenairesFormGroup(partenaires: PartenairesFormGroupInput = { id: null }): PartenairesFormGroup {
    const partenairesRawValue = {
      ...this.getFormDefaults(),
      ...partenaires,
    };
    return new FormGroup<PartenairesFormGroupContent>({
      id: new FormControl(
        { value: partenairesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(partenairesRawValue.nom),
      photo: new FormControl(partenairesRawValue.photo),
      photoContentType: new FormControl(partenairesRawValue.photoContentType),
      description: new FormControl(partenairesRawValue.description),
      lien: new FormControl(partenairesRawValue.lien),
    });
  }

  getPartenaires(form: PartenairesFormGroup): IPartenaires | NewPartenaires {
    return form.getRawValue() as IPartenaires | NewPartenaires;
  }

  resetForm(form: PartenairesFormGroup, partenaires: PartenairesFormGroupInput): void {
    const partenairesRawValue = { ...this.getFormDefaults(), ...partenaires };
    form.reset(
      {
        ...partenairesRawValue,
        id: { value: partenairesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PartenairesFormDefaults {
    return {
      id: null,
    };
  }
}
