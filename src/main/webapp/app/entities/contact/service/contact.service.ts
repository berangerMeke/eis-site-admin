import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContact, NewContact } from '../contact.model';

export type PartialUpdateContact = Partial<IContact> & Pick<IContact, 'id'>;

export type EntityResponseType = HttpResponse<IContact>;
export type EntityArrayResponseType = HttpResponse<IContact[]>;

@Injectable({ providedIn: 'root' })
export class ContactService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contacts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contact: NewContact): Observable<EntityResponseType> {
    return this.http.post<IContact>(this.resourceUrl, contact, { observe: 'response' });
  }

  update(contact: IContact): Observable<EntityResponseType> {
    return this.http.put<IContact>(`${this.resourceUrl}/${this.getContactIdentifier(contact)}`, contact, { observe: 'response' });
  }

  partialUpdate(contact: PartialUpdateContact): Observable<EntityResponseType> {
    return this.http.patch<IContact>(`${this.resourceUrl}/${this.getContactIdentifier(contact)}`, contact, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContactIdentifier(contact: Pick<IContact, 'id'>): number {
    return contact.id;
  }

  compareContact(o1: Pick<IContact, 'id'> | null, o2: Pick<IContact, 'id'> | null): boolean {
    return o1 && o2 ? this.getContactIdentifier(o1) === this.getContactIdentifier(o2) : o1 === o2;
  }

  addContactToCollectionIfMissing<Type extends Pick<IContact, 'id'>>(
    contactCollection: Type[],
    ...contactsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contacts: Type[] = contactsToCheck.filter(isPresent);
    if (contacts.length > 0) {
      const contactCollectionIdentifiers = contactCollection.map(contactItem => this.getContactIdentifier(contactItem)!);
      const contactsToAdd = contacts.filter(contactItem => {
        const contactIdentifier = this.getContactIdentifier(contactItem);
        if (contactCollectionIdentifiers.includes(contactIdentifier)) {
          return false;
        }
        contactCollectionIdentifiers.push(contactIdentifier);
        return true;
      });
      return [...contactsToAdd, ...contactCollection];
    }
    return contactCollection;
  }
}
