import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IService } from '../service.model';
import { ServiceService } from '../service/service.service';

@Injectable({ providedIn: 'root' })
export class ServiceRoutingResolveService implements Resolve<IService | null> {
  constructor(protected service: ServiceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IService | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((service: HttpResponse<IService>) => {
          if (service.body) {
            return of(service.body);
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
