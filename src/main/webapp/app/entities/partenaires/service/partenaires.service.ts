import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartenaires, NewPartenaires } from '../partenaires.model';

export type PartialUpdatePartenaires = Partial<IPartenaires> & Pick<IPartenaires, 'id'>;

export type EntityResponseType = HttpResponse<IPartenaires>;
export type EntityArrayResponseType = HttpResponse<IPartenaires[]>;

@Injectable({ providedIn: 'root' })
export class PartenairesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/partenaires');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(partenaires: NewPartenaires): Observable<EntityResponseType> {
    return this.http.post<IPartenaires>(this.resourceUrl, partenaires, { observe: 'response' });
  }

  update(partenaires: IPartenaires): Observable<EntityResponseType> {
    return this.http.put<IPartenaires>(`${this.resourceUrl}/${this.getPartenairesIdentifier(partenaires)}`, partenaires, {
      observe: 'response',
    });
  }

  partialUpdate(partenaires: PartialUpdatePartenaires): Observable<EntityResponseType> {
    return this.http.patch<IPartenaires>(`${this.resourceUrl}/${this.getPartenairesIdentifier(partenaires)}`, partenaires, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartenaires>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartenaires[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPartenairesIdentifier(partenaires: Pick<IPartenaires, 'id'>): number {
    return partenaires.id;
  }

  comparePartenaires(o1: Pick<IPartenaires, 'id'> | null, o2: Pick<IPartenaires, 'id'> | null): boolean {
    return o1 && o2 ? this.getPartenairesIdentifier(o1) === this.getPartenairesIdentifier(o2) : o1 === o2;
  }

  addPartenairesToCollectionIfMissing<Type extends Pick<IPartenaires, 'id'>>(
    partenairesCollection: Type[],
    ...partenairesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const partenaires: Type[] = partenairesToCheck.filter(isPresent);
    if (partenaires.length > 0) {
      const partenairesCollectionIdentifiers = partenairesCollection.map(
        partenairesItem => this.getPartenairesIdentifier(partenairesItem)!
      );
      const partenairesToAdd = partenaires.filter(partenairesItem => {
        const partenairesIdentifier = this.getPartenairesIdentifier(partenairesItem);
        if (partenairesCollectionIdentifiers.includes(partenairesIdentifier)) {
          return false;
        }
        partenairesCollectionIdentifiers.push(partenairesIdentifier);
        return true;
      });
      return [...partenairesToAdd, ...partenairesCollection];
    }
    return partenairesCollection;
  }
}
