import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPageEquipe } from '../page-equipe.model';
import { PageEquipeService } from '../service/page-equipe.service';

@Injectable({ providedIn: 'root' })
export class PageEquipeRoutingResolveService implements Resolve<IPageEquipe | null> {
  constructor(protected service: PageEquipeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageEquipe | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pageEquipe: HttpResponse<IPageEquipe>) => {
          if (pageEquipe.body) {
            return of(pageEquipe.body);
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
