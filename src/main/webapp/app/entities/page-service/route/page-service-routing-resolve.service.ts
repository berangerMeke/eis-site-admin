import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPageService } from '../page-service.model';
import { PageServiceService } from '../service/page-service.service';

@Injectable({ providedIn: 'root' })
export class PageServiceRoutingResolveService implements Resolve<IPageService | null> {
  constructor(protected service: PageServiceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageService | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pageService: HttpResponse<IPageService>) => {
          if (pageService.body) {
            return of(pageService.body);
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
