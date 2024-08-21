import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContact } from '../contact.model';
import { ContactService } from '../service/contact.service';

@Injectable({ providedIn: 'root' })
export class ContactRoutingResolveService implements Resolve<IContact | null> {
  constructor(protected service: ContactService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContact | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contact: HttpResponse<IContact>) => {
          if (contact.body) {
            return of(contact.body);
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
