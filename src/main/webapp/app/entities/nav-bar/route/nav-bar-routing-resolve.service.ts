import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INavBar } from '../nav-bar.model';
import { NavBarService } from '../service/nav-bar.service';

@Injectable({ providedIn: 'root' })
export class NavBarRoutingResolveService implements Resolve<INavBar | null> {
  constructor(protected service: NavBarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INavBar | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((navBar: HttpResponse<INavBar>) => {
          if (navBar.body) {
            return of(navBar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
