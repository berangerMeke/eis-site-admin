import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PageEquipeComponent } from './list/page-equipe.component';
import { PageEquipeDetailComponent } from './detail/page-equipe-detail.component';
import { PageEquipeUpdateComponent } from './update/page-equipe-update.component';
import { PageEquipeDeleteDialogComponent } from './delete/page-equipe-delete-dialog.component';
import { PageEquipeRoutingModule } from './route/page-equipe-routing.module';

@NgModule({
  imports: [SharedModule, PageEquipeRoutingModule],
  declarations: [PageEquipeComponent, PageEquipeDetailComponent, PageEquipeUpdateComponent, PageEquipeDeleteDialogComponent],
})
export class PageEquipeModule {}
