import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LienMembreEquipeComponent } from './list/lien-membre-equipe.component';
import { LienMembreEquipeDetailComponent } from './detail/lien-membre-equipe-detail.component';
import { LienMembreEquipeUpdateComponent } from './update/lien-membre-equipe-update.component';
import { LienMembreEquipeDeleteDialogComponent } from './delete/lien-membre-equipe-delete-dialog.component';
import { LienMembreEquipeRoutingModule } from './route/lien-membre-equipe-routing.module';

@NgModule({
  imports: [SharedModule, LienMembreEquipeRoutingModule],
  declarations: [
    LienMembreEquipeComponent,
    LienMembreEquipeDetailComponent,
    LienMembreEquipeUpdateComponent,
    LienMembreEquipeDeleteDialogComponent,
  ],
})
export class LienMembreEquipeModule {}
