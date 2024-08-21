import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartenairesComponent } from './list/partenaires.component';
import { PartenairesDetailComponent } from './detail/partenaires-detail.component';
import { PartenairesUpdateComponent } from './update/partenaires-update.component';
import { PartenairesDeleteDialogComponent } from './delete/partenaires-delete-dialog.component';
import { PartenairesRoutingModule } from './route/partenaires-routing.module';

@NgModule({
  imports: [SharedModule, PartenairesRoutingModule],
  declarations: [PartenairesComponent, PartenairesDetailComponent, PartenairesUpdateComponent, PartenairesDeleteDialogComponent],
})
export class PartenairesModule {}
