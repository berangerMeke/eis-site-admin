import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPageService, NewPageService } from '../page-service.model';

export type PartialUpdatePageService = Partial<IPageService> & Pick<IPageService, 'id'>;

export type EntityResponseType = HttpResponse<IPageService>;
export type EntityArrayResponseType = HttpResponse<IPageService[]>;

@Injectable({ providedIn: 'root' })
export class PageServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/page-services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pageService: NewPageService): Observable<EntityResponseType> {
    return this.http.post<IPageService>(this.resourceUrl, pageService, { observe: 'response' });
  }

  update(pageService: IPageService): Observable<EntityResponseType> {
    return this.http.put<IPageService>(`${this.resourceUrl}/${this.getPageServiceIdentifier(pageService)}`, pageService, {
      observe: 'response',
    });
  }

  partialUpdate(pageService: PartialUpdatePageService): Observable<EntityResponseType> {
    return this.http.patch<IPageService>(`${this.resourceUrl}/${this.getPageServiceIdentifier(pageService)}`, pageService, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPageServiceIdentifier(pageService: Pick<IPageService, 'id'>): number {
    return pageService.id;
  }

  comparePageService(o1: Pick<IPageService, 'id'> | null, o2: Pick<IPageService, 'id'> | null): boolean {
    return o1 && o2 ? this.getPageServiceIdentifier(o1) === this.getPageServiceIdentifier(o2) : o1 === o2;
  }

  addPageServiceToCollectionIfMissing<Type extends Pick<IPageService, 'id'>>(
    pageServiceCollection: Type[],
    ...pageServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pageServices: Type[] = pageServicesToCheck.filter(isPresent);
    if (pageServices.length > 0) {
      const pageServiceCollectionIdentifiers = pageServiceCollection.map(
        pageServiceItem => this.getPageServiceIdentifier(pageServiceItem)!
      );
      const pageServicesToAdd = pageServices.filter(pageServiceItem => {
        const pageServiceIdentifier = this.getPageServiceIdentifier(pageServiceItem);
        if (pageServiceCollectionIdentifiers.includes(pageServiceIdentifier)) {
          return false;
        }
        pageServiceCollectionIdentifiers.push(pageServiceIdentifier);
        return true;
      });
      return [...pageServicesToAdd, ...pageServiceCollection];
    }
    return pageServiceCollection;
  }
}
