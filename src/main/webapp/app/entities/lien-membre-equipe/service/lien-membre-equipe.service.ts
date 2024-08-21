import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILienMembreEquipe, NewLienMembreEquipe } from '../lien-membre-equipe.model';

export type PartialUpdateLienMembreEquipe = Partial<ILienMembreEquipe> & Pick<ILienMembreEquipe, 'id'>;

export type EntityResponseType = HttpResponse<ILienMembreEquipe>;
export type EntityArrayResponseType = HttpResponse<ILienMembreEquipe[]>;

@Injectable({ providedIn: 'root' })
export class LienMembreEquipeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lien-membre-equipes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lienMembreEquipe: NewLienMembreEquipe): Observable<EntityResponseType> {
    return this.http.post<ILienMembreEquipe>(this.resourceUrl, lienMembreEquipe, { observe: 'response' });
  }

  update(lienMembreEquipe: ILienMembreEquipe): Observable<EntityResponseType> {
    return this.http.put<ILienMembreEquipe>(
      `${this.resourceUrl}/${this.getLienMembreEquipeIdentifier(lienMembreEquipe)}`,
      lienMembreEquipe,
      { observe: 'response' }
    );
  }

  partialUpdate(lienMembreEquipe: PartialUpdateLienMembreEquipe): Observable<EntityResponseType> {
    return this.http.patch<ILienMembreEquipe>(
      `${this.resourceUrl}/${this.getLienMembreEquipeIdentifier(lienMembreEquipe)}`,
      lienMembreEquipe,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILienMembreEquipe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILienMembreEquipe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLienMembreEquipeIdentifier(lienMembreEquipe: Pick<ILienMembreEquipe, 'id'>): number {
    return lienMembreEquipe.id;
  }

  compareLienMembreEquipe(o1: Pick<ILienMembreEquipe, 'id'> | null, o2: Pick<ILienMembreEquipe, 'id'> | null): boolean {
    return o1 && o2 ? this.getLienMembreEquipeIdentifier(o1) === this.getLienMembreEquipeIdentifier(o2) : o1 === o2;
  }

  addLienMembreEquipeToCollectionIfMissing<Type extends Pick<ILienMembreEquipe, 'id'>>(
    lienMembreEquipeCollection: Type[],
    ...lienMembreEquipesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const lienMembreEquipes: Type[] = lienMembreEquipesToCheck.filter(isPresent);
    if (lienMembreEquipes.length > 0) {
      const lienMembreEquipeCollectionIdentifiers = lienMembreEquipeCollection.map(
        lienMembreEquipeItem => this.getLienMembreEquipeIdentifier(lienMembreEquipeItem)!
      );
      const lienMembreEquipesToAdd = lienMembreEquipes.filter(lienMembreEquipeItem => {
        const lienMembreEquipeIdentifier = this.getLienMembreEquipeIdentifier(lienMembreEquipeItem);
        if (lienMembreEquipeCollectionIdentifiers.includes(lienMembreEquipeIdentifier)) {
          return false;
        }
        lienMembreEquipeCollectionIdentifiers.push(lienMembreEquipeIdentifier);
        return true;
      });
      return [...lienMembreEquipesToAdd, ...lienMembreEquipeCollection];
    }
    return lienMembreEquipeCollection;
  }
}
