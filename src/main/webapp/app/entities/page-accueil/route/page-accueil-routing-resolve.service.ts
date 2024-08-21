import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPageAccueil } from '../page-accueil.model';
import { PageAccueilService } from '../service/page-accueil.service';

@Injectable({ providedIn: 'root' })
export class PageAccueilRoutingResolveService implements Resolve<IPageAccueil | null> {
  constructor(protected service: PageAccueilService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageAccueil | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pageAccueil: HttpResponse<IPageAccueil>) => {
          if (pageAccueil.body) {
            return of(pageAccueil.body);
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
