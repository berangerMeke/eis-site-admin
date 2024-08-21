import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageAPropos } from '../page-a-propos.model';
import { PageAProposService } from '../service/page-a-propos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './page-a-propos-delete-dialog.component.html',
})
export class PageAProposDeleteDialogComponent {
  pageAPropos?: IPageAPropos;

  constructor(protected pageAProposService: PageAProposService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageAProposService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
