import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMembreEquipe } from '../membre-equipe.model';
import { MembreEquipeService } from '../service/membre-equipe.service';

@Injectable({ providedIn: 'root' })
export class MembreEquipeRoutingResolveService implements Resolve<IMembreEquipe | null> {
  constructor(protected service: MembreEquipeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembreEquipe | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((membreEquipe: HttpResponse<IMembreEquipe>) => {
          if (membreEquipe.body) {
            return of(membreEquipe.body);
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
