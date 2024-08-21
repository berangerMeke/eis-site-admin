import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILienMembreEquipe } from '../lien-membre-equipe.model';
import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';

@Injectable({ providedIn: 'root' })
export class LienMembreEquipeRoutingResolveService implements Resolve<ILienMembreEquipe | null> {
  constructor(protected service: LienMembreEquipeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILienMembreEquipe | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lienMembreEquipe: HttpResponse<ILienMembreEquipe>) => {
          if (lienMembreEquipe.body) {
            return of(lienMembreEquipe.body);
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
