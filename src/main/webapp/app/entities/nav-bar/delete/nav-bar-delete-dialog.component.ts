import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INavBar } from '../nav-bar.model';
import { NavBarService } from '../service/nav-bar.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nav-bar-delete-dialog.component.html',
})
export class NavBarDeleteDialogComponent {
  navBar?: INavBar;

  constructor(protected navBarService: NavBarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.navBarService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
