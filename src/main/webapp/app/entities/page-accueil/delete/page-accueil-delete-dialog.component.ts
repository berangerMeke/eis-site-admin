import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageAccueil } from '../page-accueil.model';
import { PageAccueilService } from '../service/page-accueil.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './page-accueil-delete-dialog.component.html',
})
export class PageAccueilDeleteDialogComponent {
  pageAccueil?: IPageAccueil;

  constructor(protected pageAccueilService: PageAccueilService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageAccueilService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
