import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormationComponent } from './list/formation.component';
import { FormationDetailComponent } from './detail/formation-detail.component';
import { FormationUpdateComponent } from './update/formation-update.component';
import { FormationDeleteDialogComponent } from './delete/formation-delete-dialog.component';
import { FormationRoutingModule } from './route/formation-routing.module';

@NgModule({
  imports: [SharedModule, FormationRoutingModule],
  declarations: [FormationComponent, FormationDetailComponent, FormationUpdateComponent, FormationDeleteDialogComponent],
})
export class FormationModule {}
