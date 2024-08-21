import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPageAPropos } from '../page-a-propos.model';
import { PageAProposService } from '../service/page-a-propos.service';

@Injectable({ providedIn: 'root' })
export class PageAProposRoutingResolveService implements Resolve<IPageAPropos | null> {
  constructor(protected service: PageAProposService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageAPropos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pageAPropos: HttpResponse<IPageAPropos>) => {
          if (pageAPropos.body) {
            return of(pageAPropos.body);
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
