import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INavBar, NewNavBar } from '../nav-bar.model';

export type PartialUpdateNavBar = Partial<INavBar> & Pick<INavBar, 'id'>;

export type EntityResponseType = HttpResponse<INavBar>;
export type EntityArrayResponseType = HttpResponse<INavBar[]>;

@Injectable({ providedIn: 'root' })
export class NavBarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nav-bars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(navBar: NewNavBar): Observable<EntityResponseType> {
    return this.http.post<INavBar>(this.resourceUrl, navBar, { observe: 'response' });
  }

  update(navBar: INavBar): Observable<EntityResponseType> {
    return this.http.put<INavBar>(`${this.resourceUrl}/${this.getNavBarIdentifier(navBar)}`, navBar, { observe: 'response' });
  }

  partialUpdate(navBar: PartialUpdateNavBar): Observable<EntityResponseType> {
    return this.http.patch<INavBar>(`${this.resourceUrl}/${this.getNavBarIdentifier(navBar)}`, navBar, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INavBar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INavBar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNavBarIdentifier(navBar: Pick<INavBar, 'id'>): number {
    return navBar.id;
  }

  compareNavBar(o1: Pick<INavBar, 'id'> | null, o2: Pick<INavBar, 'id'> | null): boolean {
    return o1 && o2 ? this.getNavBarIdentifier(o1) === this.getNavBarIdentifier(o2) : o1 === o2;
  }

  addNavBarToCollectionIfMissing<Type extends Pick<INavBar, 'id'>>(
    navBarCollection: Type[],
    ...navBarsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const navBars: Type[] = navBarsToCheck.filter(isPresent);
    if (navBars.length > 0) {
      const navBarCollectionIdentifiers = navBarCollection.map(navBarItem => this.getNavBarIdentifier(navBarItem)!);
      const navBarsToAdd = navBars.filter(navBarItem => {
        const navBarIdentifier = this.getNavBarIdentifier(navBarItem);
        if (navBarCollectionIdentifiers.includes(navBarIdentifier)) {
          return false;
        }
        navBarCollectionIdentifiers.push(navBarIdentifier);
        return true;
      });
      return [...navBarsToAdd, ...navBarCollection];
    }
    return navBarCollection;
  }
}
