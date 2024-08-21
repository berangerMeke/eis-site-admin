import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMembreEquipe, NewMembreEquipe } from '../membre-equipe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMembreEquipe for edit and NewMembreEquipeFormGroupInput for create.
 */
type MembreEquipeFormGroupInput = IMembreEquipe | PartialWithRequiredKeyOf<NewMembreEquipe>;

type MembreEquipeFormDefaults = Pick<NewMembreEquipe, 'id'>;

type MembreEquipeFormGroupContent = {
  id: FormControl<IMembreEquipe['id'] | NewMembreEquipe['id']>;
  nom: FormControl<IMembreEquipe['nom']>;
  prenom: FormControl<IMembreEquipe['prenom']>;
  telephone: FormControl<IMembreEquipe['telephone']>;
  adresseMail: FormControl<IMembreEquipe['adresseMail']>;
  certification: FormControl<IMembreEquipe['certification']>;
  diplome: FormControl<IMembreEquipe['diplome']>;
  niveauEtude: FormControl<IMembreEquipe['niveauEtude']>;
  photo: FormControl<IMembreEquipe['photo']>;
  photoContentType: FormControl<IMembreEquipe['photoContentType']>;
  description: FormControl<IMembreEquipe['description']>;
  message: FormControl<IMembreEquipe['message']>;
  fonction: FormControl<IMembreEquipe['fonction']>;
  pageEquipe1: FormControl<IMembreEquipe['pageEquipe1']>;
};

export type MembreEquipeFormGroup = FormGroup<MembreEquipeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MembreEquipeFormService {
  createMembreEquipeFormGroup(membreEquipe: MembreEquipeFormGroupInput = { id: null }): MembreEquipeFormGroup {
    const membreEquipeRawValue = {
      ...this.getFormDefaults(),
      ...membreEquipe,
    };
    return new FormGroup<MembreEquipeFormGroupContent>({
      id: new FormControl(
        { value: membreEquipeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(membreEquipeRawValue.nom),
      prenom: new FormControl(membreEquipeRawValue.prenom),
      telephone: new FormControl(membreEquipeRawValue.telephone),
      adresseMail: new FormControl(membreEquipeRawValue.adresseMail),
      certification: new FormControl(membreEquipeRawValue.certification),
      diplome: new FormControl(membreEquipeRawValue.diplome),
      niveauEtude: new FormControl(membreEquipeRawValue.niveauEtude),
      photo: new FormControl(membreEquipeRawValue.photo),
      photoContentType: new FormControl(membreEquipeRawValue.photoContentType),
      description: new FormControl(membreEquipeRawValue.description),
      message: new FormControl(membreEquipeRawValue.message),
      fonction: new FormControl(membreEquipeRawValue.fonction),
      pageEquipe1: new FormControl(membreEquipeRawValue.pageEquipe1),
    });
  }

  getMembreEquipe(form: MembreEquipeFormGroup): IMembreEquipe | NewMembreEquipe {
    return form.getRawValue() as IMembreEquipe | NewMembreEquipe;
  }

  resetForm(form: MembreEquipeFormGroup, membreEquipe: MembreEquipeFormGroupInput): void {
    const membreEquipeRawValue = { ...this.getFormDefaults(), ...membreEquipe };
    form.reset(
      {
        ...membreEquipeRawValue,
        id: { value: membreEquipeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MembreEquipeFormDefaults {
    return {
      id: null,
    };
  }
}
