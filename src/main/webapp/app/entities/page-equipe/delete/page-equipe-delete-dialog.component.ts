import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageEquipe } from '../page-equipe.model';
import { PageEquipeService } from '../service/page-equipe.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './page-equipe-delete-dialog.component.html',
})
export class PageEquipeDeleteDialogComponent {
  pageEquipe?: IPageEquipe;

  constructor(protected pageEquipeService: PageEquipeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageEquipeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
