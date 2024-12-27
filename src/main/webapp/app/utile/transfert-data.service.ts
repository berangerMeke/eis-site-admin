import { Injectable, EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransfertDataService {

  subject = new Subject<any>();

  constructor() { }

  emettre<T>(data: T) {
    console.log(data);
    this.subject.next(data);

  }

  recevoir<T>(next: (data: T) => void) {
    return this.subject.asObservable().subscribe(next);
  }
}
