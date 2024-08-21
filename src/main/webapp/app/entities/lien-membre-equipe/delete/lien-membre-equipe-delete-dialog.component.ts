import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILienMembreEquipe } from '../lien-membre-equipe.model';
import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './lien-membre-equipe-delete-dialog.component.html',
})
export class LienMembreEquipeDeleteDialogComponent {
  lienMembreEquipe?: ILienMembreEquipe;

  constructor(protected lienMembreEquipeService: LienMembreEquipeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lienMembreEquipeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
