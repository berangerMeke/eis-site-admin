import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPageAccueil, NewPageAccueil } from '../page-accueil.model';

export type PartialUpdatePageAccueil = Partial<IPageAccueil> & Pick<IPageAccueil, 'id'>;

export type EntityResponseType = HttpResponse<IPageAccueil>;
export type EntityArrayResponseType = HttpResponse<IPageAccueil[]>;

@Injectable({ providedIn: 'root' })
export class PageAccueilService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/page-accueils');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pageAccueil: NewPageAccueil): Observable<EntityResponseType> {
    return this.http.post<IPageAccueil>(this.resourceUrl, pageAccueil, { observe: 'response' });
  }

  update(pageAccueil: IPageAccueil): Observable<EntityResponseType> {
    return this.http.put<IPageAccueil>(`${this.resourceUrl}/${this.getPageAccueilIdentifier(pageAccueil)}`, pageAccueil, {
      observe: 'response',
    });
  }

  partialUpdate(pageAccueil: PartialUpdatePageAccueil): Observable<EntityResponseType> {
    return this.http.patch<IPageAccueil>(`${this.resourceUrl}/${this.getPageAccueilIdentifier(pageAccueil)}`, pageAccueil, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageAccueil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageAccueil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPageAccueilIdentifier(pageAccueil: Pick<IPageAccueil, 'id'>): number {
    return pageAccueil.id;
  }

  comparePageAccueil(o1: Pick<IPageAccueil, 'id'> | null, o2: Pick<IPageAccueil, 'id'> | null): boolean {
    return o1 && o2 ? this.getPageAccueilIdentifier(o1) === this.getPageAccueilIdentifier(o2) : o1 === o2;
  }

  addPageAccueilToCollectionIfMissing<Type extends Pick<IPageAccueil, 'id'>>(
    pageAccueilCollection: Type[],
    ...pageAccueilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pageAccueils: Type[] = pageAccueilsToCheck.filter(isPresent);
    if (pageAccueils.length > 0) {
      const pageAccueilCollectionIdentifiers = pageAccueilCollection.map(
        pageAccueilItem => this.getPageAccueilIdentifier(pageAccueilItem)!
      );
      const pageAccueilsToAdd = pageAccueils.filter(pageAccueilItem => {
        const pageAccueilIdentifier = this.getPageAccueilIdentifier(pageAccueilItem);
        if (pageAccueilCollectionIdentifiers.includes(pageAccueilIdentifier)) {
          return false;
        }
        pageAccueilCollectionIdentifiers.push(pageAccueilIdentifier);
        return true;
      });
      return [...pageAccueilsToAdd, ...pageAccueilCollection];
    }
    return pageAccueilCollection;
  }
}
