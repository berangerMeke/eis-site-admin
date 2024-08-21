import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IService, NewService } from '../service.model';

export type PartialUpdateService = Partial<IService> & Pick<IService, 'id'>;

export type EntityResponseType = HttpResponse<IService>;
export type EntityArrayResponseType = HttpResponse<IService[]>;

@Injectable({ providedIn: 'root' })
export class ServiceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(service: NewService): Observable<EntityResponseType> {
    return this.http.post<IService>(this.resourceUrl, service, { observe: 'response' });
  }

  update(service: IService): Observable<EntityResponseType> {
    return this.http.put<IService>(`${this.resourceUrl}/${this.getServiceIdentifier(service)}`, service, { observe: 'response' });
  }

  partialUpdate(service: PartialUpdateService): Observable<EntityResponseType> {
    return this.http.patch<IService>(`${this.resourceUrl}/${this.getServiceIdentifier(service)}`, service, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServiceIdentifier(service: Pick<IService, 'id'>): number {
    return service.id;
  }

  compareService(o1: Pick<IService, 'id'> | null, o2: Pick<IService, 'id'> | null): boolean {
    return o1 && o2 ? this.getServiceIdentifier(o1) === this.getServiceIdentifier(o2) : o1 === o2;
  }

  addServiceToCollectionIfMissing<Type extends Pick<IService, 'id'>>(
    serviceCollection: Type[],
    ...servicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const services: Type[] = servicesToCheck.filter(isPresent);
    if (services.length > 0) {
      const serviceCollectionIdentifiers = serviceCollection.map(serviceItem => this.getServiceIdentifier(serviceItem)!);
      const servicesToAdd = services.filter(serviceItem => {
        const serviceIdentifier = this.getServiceIdentifier(serviceItem);
        if (serviceCollectionIdentifiers.includes(serviceIdentifier)) {
          return false;
        }
        serviceCollectionIdentifiers.push(serviceIdentifier);
        return true;
      });
      return [...servicesToAdd, ...serviceCollection];
    }
    return serviceCollection;
  }
}
