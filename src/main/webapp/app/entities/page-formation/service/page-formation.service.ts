import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPageFormation, NewPageFormation } from '../page-formation.model';

export type PartialUpdatePageFormation = Partial<IPageFormation> & Pick<IPageFormation, 'id'>;

export type EntityResponseType = HttpResponse<IPageFormation>;
export type EntityArrayResponseType = HttpResponse<IPageFormation[]>;

@Injectable({ providedIn: 'root' })
export class PageFormationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/page-formations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pageFormation: NewPageFormation): Observable<EntityResponseType> {
    return this.http.post<IPageFormation>(this.resourceUrl, pageFormation, { observe: 'response' });
  }

  update(pageFormation: IPageFormation): Observable<EntityResponseType> {
    return this.http.put<IPageFormation>(`${this.resourceUrl}/${this.getPageFormationIdentifier(pageFormation)}`, pageFormation, {
      observe: 'response',
    });
  }

  partialUpdate(pageFormation: PartialUpdatePageFormation): Observable<EntityResponseType> {
    return this.http.patch<IPageFormation>(`${this.resourceUrl}/${this.getPageFormationIdentifier(pageFormation)}`, pageFormation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageFormation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageFormation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPageFormationIdentifier(pageFormation: Pick<IPageFormation, 'id'>): number {
    return pageFormation.id;
  }

  comparePageFormation(o1: Pick<IPageFormation, 'id'> | null, o2: Pick<IPageFormation, 'id'> | null): boolean {
    return o1 && o2 ? this.getPageFormationIdentifier(o1) === this.getPageFormationIdentifier(o2) : o1 === o2;
  }

  addPageFormationToCollectionIfMissing<Type extends Pick<IPageFormation, 'id'>>(
    pageFormationCollection: Type[],
    ...pageFormationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pageFormations: Type[] = pageFormationsToCheck.filter(isPresent);
    if (pageFormations.length > 0) {
      const pageFormationCollectionIdentifiers = pageFormationCollection.map(
        pageFormationItem => this.getPageFormationIdentifier(pageFormationItem)!
      );
      const pageFormationsToAdd = pageFormations.filter(pageFormationItem => {
        const pageFormationIdentifier = this.getPageFormationIdentifier(pageFormationItem);
        if (pageFormationCollectionIdentifiers.includes(pageFormationIdentifier)) {
          return false;
        }
        pageFormationCollectionIdentifiers.push(pageFormationIdentifier);
        return true;
      });
      return [...pageFormationsToAdd, ...pageFormationCollection];
    }
    return pageFormationCollection;
  }
}
