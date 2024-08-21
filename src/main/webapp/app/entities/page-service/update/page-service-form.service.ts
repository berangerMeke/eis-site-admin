import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPageService, NewPageService } from '../page-service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPageService for edit and NewPageServiceFormGroupInput for create.
 */
type PageServiceFormGroupInput = IPageService | PartialWithRequiredKeyOf<NewPageService>;

type PageServiceFormDefaults = Pick<NewPageService, 'id'>;

type PageServiceFormGroupContent = {
  id: FormControl<IPageService['id'] | NewPageService['id']>;
  titre: FormControl<IPageService['titre']>;
  soustitre: FormControl<IPageService['soustitre']>;
};

export type PageServiceFormGroup = FormGroup<PageServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PageServiceFormService {
  createPageServiceFormGroup(pageService: PageServiceFormGroupInput = { id: null }): PageServiceFormGroup {
    const pageServiceRawValue = {
      ...this.getFormDefaults(),
      ...pageService,
    };
    return new FormGroup<PageServiceFormGroupContent>({
      id: new FormControl(
        { value: pageServiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titre: new FormControl(pageServiceRawValue.titre),
      soustitre: new FormControl(pageServiceRawValue.soustitre),
    });
  }

  getPageService(form: PageServiceFormGroup): IPageService | NewPageService {
    return form.getRawValue() as IPageService | NewPageService;
  }

  resetForm(form: PageServiceFormGroup, pageService: PageServiceFormGroupInput): void {
    const pageServiceRawValue = { ...this.getFormDefaults(), ...pageService };
    form.reset(
      {
        ...pageServiceRawValue,
        id: { value: pageServiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PageServiceFormDefaults {
    return {
      id: null,
    };
  }
}
