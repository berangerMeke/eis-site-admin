import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INavBar, NewNavBar } from '../nav-bar.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INavBar for edit and NewNavBarFormGroupInput for create.
 */
type NavBarFormGroupInput = INavBar | PartialWithRequiredKeyOf<NewNavBar>;

type NavBarFormDefaults = Pick<NewNavBar, 'id'>;

type NavBarFormGroupContent = {
  id: FormControl<INavBar['id'] | NewNavBar['id']>;
  logo: FormControl<INavBar['logo']>;
  logoContentType: FormControl<INavBar['logoContentType']>;
  menu1: FormControl<INavBar['menu1']>;
  menu2: FormControl<INavBar['menu2']>;
  menu3: FormControl<INavBar['menu3']>;
  menu4: FormControl<INavBar['menu4']>;
  menu5: FormControl<INavBar['menu5']>;
  menu6: FormControl<INavBar['menu6']>;
};

export type NavBarFormGroup = FormGroup<NavBarFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NavBarFormService {
  createNavBarFormGroup(navBar: NavBarFormGroupInput = { id: null }): NavBarFormGroup {
    const navBarRawValue = {
      ...this.getFormDefaults(),
      ...navBar,
    };
    return new FormGroup<NavBarFormGroupContent>({
      id: new FormControl(
        { value: navBarRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      logo: new FormControl(navBarRawValue.logo),
      logoContentType: new FormControl(navBarRawValue.logoContentType),
      menu1: new FormControl(navBarRawValue.menu1),
      menu2: new FormControl(navBarRawValue.menu2),
      menu3: new FormControl(navBarRawValue.menu3),
      menu4: new FormControl(navBarRawValue.menu4),
      menu5: new FormControl(navBarRawValue.menu5),
      menu6: new FormControl(navBarRawValue.menu6),
    });
  }

  getNavBar(form: NavBarFormGroup): INavBar | NewNavBar {
    return form.getRawValue() as INavBar | NewNavBar;
  }

  resetForm(form: NavBarFormGroup, navBar: NavBarFormGroupInput): void {
    const navBarRawValue = { ...this.getFormDefaults(), ...navBar };
    form.reset(
      {
        ...navBarRawValue,
        id: { value: navBarRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NavBarFormDefaults {
    return {
      id: null,
    };
  }
}
