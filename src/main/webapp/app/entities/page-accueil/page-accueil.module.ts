import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PageAccueilComponent } from './list/page-accueil.component';
import { PageAccueilDetailComponent } from './detail/page-accueil-detail.component';
import { PageAccueilUpdateComponent } from './update/page-accueil-update.component';
import { PageAccueilDeleteDialogComponent } from './delete/page-accueil-delete-dialog.component';
import { PageAccueilRoutingModule } from './route/page-accueil-routing.module';

@NgModule({
  imports: [SharedModule, PageAccueilRoutingModule],
  declarations: [PageAccueilComponent, PageAccueilDetailComponent, PageAccueilUpdateComponent, PageAccueilDeleteDialogComponent],
})
export class PageAccueilModule {}
