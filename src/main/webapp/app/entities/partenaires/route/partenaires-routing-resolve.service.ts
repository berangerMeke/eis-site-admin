import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartenaires } from '../partenaires.model';
import { PartenairesService } from '../service/partenaires.service';

@Injectable({ providedIn: 'root' })
export class PartenairesRoutingResolveService implements Resolve<IPartenaires | null> {
  constructor(protected service: PartenairesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartenaires | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((partenaires: HttpResponse<IPartenaires>) => {
          if (partenaires.body) {
            return of(partenaires.body);
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
