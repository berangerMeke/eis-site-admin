import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartenaires } from '../partenaires.model';
import { PartenairesService } from '../service/partenaires.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './partenaires-delete-dialog.component.html',
})
export class PartenairesDeleteDialogComponent {
  partenaires?: IPartenaires;

  constructor(protected partenairesService: PartenairesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partenairesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
