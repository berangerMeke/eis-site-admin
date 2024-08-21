import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPageFormation } from '../page-formation.model';
import { PageFormationService } from '../service/page-formation.service';

@Injectable({ providedIn: 'root' })
export class PageFormationRoutingResolveService implements Resolve<IPageFormation | null> {
  constructor(protected service: PageFormationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageFormation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pageFormation: HttpResponse<IPageFormation>) => {
          if (pageFormation.body) {
            return of(pageFormation.body);
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
