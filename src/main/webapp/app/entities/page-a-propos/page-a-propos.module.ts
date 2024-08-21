import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PageAProposComponent } from './list/page-a-propos.component';
import { PageAProposDetailComponent } from './detail/page-a-propos-detail.component';
import { PageAProposUpdateComponent } from './update/page-a-propos-update.component';
import { PageAProposDeleteDialogComponent } from './delete/page-a-propos-delete-dialog.component';
import { PageAProposRoutingModule } from './route/page-a-propos-routing.module';

@NgModule({
  imports: [SharedModule, PageAProposRoutingModule],
  declarations: [PageAProposComponent, PageAProposDetailComponent, PageAProposUpdateComponent, PageAProposDeleteDialogComponent],
})
export class PageAProposModule {}
