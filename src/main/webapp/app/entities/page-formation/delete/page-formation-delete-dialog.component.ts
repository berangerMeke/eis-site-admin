import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageFormation } from '../page-formation.model';
import { PageFormationService } from '../service/page-formation.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './page-formation-delete-dialog.component.html',
})
export class PageFormationDeleteDialogComponent {
  pageFormation?: IPageFormation;

  constructor(protected pageFormationService: PageFormationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageFormationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
