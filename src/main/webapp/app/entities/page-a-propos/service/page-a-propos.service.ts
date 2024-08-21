import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPageAPropos, NewPageAPropos } from '../page-a-propos.model';

export type PartialUpdatePageAPropos = Partial<IPageAPropos> & Pick<IPageAPropos, 'id'>;

export type EntityResponseType = HttpResponse<IPageAPropos>;
export type EntityArrayResponseType = HttpResponse<IPageAPropos[]>;

@Injectable({ providedIn: 'root' })
export class PageAProposService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/page-a-propos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pageAPropos: NewPageAPropos): Observable<EntityResponseType> {
    return this.http.post<IPageAPropos>(this.resourceUrl, pageAPropos, { observe: 'response' });
  }

  update(pageAPropos: IPageAPropos): Observable<EntityResponseType> {
    return this.http.put<IPageAPropos>(`${this.resourceUrl}/${this.getPageAProposIdentifier(pageAPropos)}`, pageAPropos, {
      observe: 'response',
    });
  }

  partialUpdate(pageAPropos: PartialUpdatePageAPropos): Observable<EntityResponseType> {
    return this.http.patch<IPageAPropos>(`${this.resourceUrl}/${this.getPageAProposIdentifier(pageAPropos)}`, pageAPropos, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageAPropos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageAPropos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPageAProposIdentifier(pageAPropos: Pick<IPageAPropos, 'id'>): number {
    return pageAPropos.id;
  }

  comparePageAPropos(o1: Pick<IPageAPropos, 'id'> | null, o2: Pick<IPageAPropos, 'id'> | null): boolean {
    return o1 && o2 ? this.getPageAProposIdentifier(o1) === this.getPageAProposIdentifier(o2) : o1 === o2;
  }

  addPageAProposToCollectionIfMissing<Type extends Pick<IPageAPropos, 'id'>>(
    pageAProposCollection: Type[],
    ...pageAProposToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pageAPropos: Type[] = pageAProposToCheck.filter(isPresent);
    if (pageAPropos.length > 0) {
      const pageAProposCollectionIdentifiers = pageAProposCollection.map(
        pageAProposItem => this.getPageAProposIdentifier(pageAProposItem)!
      );
      const pageAProposToAdd = pageAPropos.filter(pageAProposItem => {
        const pageAProposIdentifier = this.getPageAProposIdentifier(pageAProposItem);
        if (pageAProposCollectionIdentifiers.includes(pageAProposIdentifier)) {
          return false;
        }
        pageAProposCollectionIdentifiers.push(pageAProposIdentifier);
        return true;
      });
      return [...pageAProposToAdd, ...pageAProposCollection];
    }
    return pageAProposCollection;
  }
}
