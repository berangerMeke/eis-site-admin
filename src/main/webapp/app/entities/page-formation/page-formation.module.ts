import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PageFormationComponent } from './list/page-formation.component';
import { PageFormationDetailComponent } from './detail/page-formation-detail.component';
import { PageFormationUpdateComponent } from './update/page-formation-update.component';
import { PageFormationDeleteDialogComponent } from './delete/page-formation-delete-dialog.component';
import { PageFormationRoutingModule } from './route/page-formation-routing.module';

@NgModule({
  imports: [SharedModule, PageFormationRoutingModule],
  declarations: [PageFormationComponent, PageFormationDetailComponent, PageFormationUpdateComponent, PageFormationDeleteDialogComponent],
})
export class PageFormationModule {}
