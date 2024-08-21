import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPageEquipe, NewPageEquipe } from '../page-equipe.model';

export type PartialUpdatePageEquipe = Partial<IPageEquipe> & Pick<IPageEquipe, 'id'>;

export type EntityResponseType = HttpResponse<IPageEquipe>;
export type EntityArrayResponseType = HttpResponse<IPageEquipe[]>;

@Injectable({ providedIn: 'root' })
export class PageEquipeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/page-equipes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pageEquipe: NewPageEquipe): Observable<EntityResponseType> {
    return this.http.post<IPageEquipe>(this.resourceUrl, pageEquipe, { observe: 'response' });
  }

  update(pageEquipe: IPageEquipe): Observable<EntityResponseType> {
    return this.http.put<IPageEquipe>(`${this.resourceUrl}/${this.getPageEquipeIdentifier(pageEquipe)}`, pageEquipe, {
      observe: 'response',
    });
  }

  partialUpdate(pageEquipe: PartialUpdatePageEquipe): Observable<EntityResponseType> {
    return this.http.patch<IPageEquipe>(`${this.resourceUrl}/${this.getPageEquipeIdentifier(pageEquipe)}`, pageEquipe, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageEquipe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageEquipe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPageEquipeIdentifier(pageEquipe: Pick<IPageEquipe, 'id'>): number {
    return pageEquipe.id;
  }

  comparePageEquipe(o1: Pick<IPageEquipe, 'id'> | null, o2: Pick<IPageEquipe, 'id'> | null): boolean {
    return o1 && o2 ? this.getPageEquipeIdentifier(o1) === this.getPageEquipeIdentifier(o2) : o1 === o2;
  }

  addPageEquipeToCollectionIfMissing<Type extends Pick<IPageEquipe, 'id'>>(
    pageEquipeCollection: Type[],
    ...pageEquipesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pageEquipes: Type[] = pageEquipesToCheck.filter(isPresent);
    if (pageEquipes.length > 0) {
      const pageEquipeCollectionIdentifiers = pageEquipeCollection.map(pageEquipeItem => this.getPageEquipeIdentifier(pageEquipeItem)!);
      const pageEquipesToAdd = pageEquipes.filter(pageEquipeItem => {
        const pageEquipeIdentifier = this.getPageEquipeIdentifier(pageEquipeItem);
        if (pageEquipeCollectionIdentifiers.includes(pageEquipeIdentifier)) {
          return false;
        }
        pageEquipeCollectionIdentifiers.push(pageEquipeIdentifier);
        return true;
      });
      return [...pageEquipesToAdd, ...pageEquipeCollection];
    }
    return pageEquipeCollection;
  }
}
