import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMembreEquipe, NewMembreEquipe } from '../membre-equipe.model';

export type PartialUpdateMembreEquipe = Partial<IMembreEquipe> & Pick<IMembreEquipe, 'id'>;

export type EntityResponseType = HttpResponse<IMembreEquipe>;
export type EntityArrayResponseType = HttpResponse<IMembreEquipe[]>;

@Injectable({ providedIn: 'root' })
export class MembreEquipeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/membre-equipes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(membreEquipe: NewMembreEquipe): Observable<EntityResponseType> {
    return this.http.post<IMembreEquipe>(this.resourceUrl, membreEquipe, { observe: 'response' });
  }

  update(membreEquipe: IMembreEquipe): Observable<EntityResponseType> {
    return this.http.put<IMembreEquipe>(`${this.resourceUrl}/${this.getMembreEquipeIdentifier(membreEquipe)}`, membreEquipe, {
      observe: 'response',
    });
  }

  partialUpdate(membreEquipe: PartialUpdateMembreEquipe): Observable<EntityResponseType> {
    return this.http.patch<IMembreEquipe>(`${this.resourceUrl}/${this.getMembreEquipeIdentifier(membreEquipe)}`, membreEquipe, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMembreEquipe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMembreEquipe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMembreEquipeIdentifier(membreEquipe: Pick<IMembreEquipe, 'id'>): number {
    return membreEquipe.id;
  }

  compareMembreEquipe(o1: Pick<IMembreEquipe, 'id'> | null, o2: Pick<IMembreEquipe, 'id'> | null): boolean {
    return o1 && o2 ? this.getMembreEquipeIdentifier(o1) === this.getMembreEquipeIdentifier(o2) : o1 === o2;
  }

  addMembreEquipeToCollectionIfMissing<Type extends Pick<IMembreEquipe, 'id'>>(
    membreEquipeCollection: Type[],
    ...membreEquipesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const membreEquipes: Type[] = membreEquipesToCheck.filter(isPresent);
    if (membreEquipes.length > 0) {
      const membreEquipeCollectionIdentifiers = membreEquipeCollection.map(
        membreEquipeItem => this.getMembreEquipeIdentifier(membreEquipeItem)!
      );
      const membreEquipesToAdd = membreEquipes.filter(membreEquipeItem => {
        const membreEquipeIdentifier = this.getMembreEquipeIdentifier(membreEquipeItem);
        if (membreEquipeCollectionIdentifiers.includes(membreEquipeIdentifier)) {
          return false;
        }
        membreEquipeCollectionIdentifiers.push(membreEquipeIdentifier);
        return true;
      });
      return [...membreEquipesToAdd, ...membreEquipeCollection];
    }
    return membreEquipeCollection;
  }
}
