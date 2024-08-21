import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PageServiceComponent } from './list/page-service.component';
import { PageServiceDetailComponent } from './detail/page-service-detail.component';
import { PageServiceUpdateComponent } from './update/page-service-update.component';
import { PageServiceDeleteDialogComponent } from './delete/page-service-delete-dialog.component';
import { PageServiceRoutingModule } from './route/page-service-routing.module';

@NgModule({
  imports: [SharedModule, PageServiceRoutingModule],
  declarations: [PageServiceComponent, PageServiceDetailComponent, PageServiceUpdateComponent, PageServiceDeleteDialogComponent],
})
export class PageServiceModule {}
