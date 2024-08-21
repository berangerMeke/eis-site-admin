import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceComponent } from './list/service.component';
import { ServiceDetailComponent } from './detail/service-detail.component';
import { ServiceUpdateComponent } from './update/service-update.component';
import { ServiceDeleteDialogComponent } from './delete/service-delete-dialog.component';
import { ServiceRoutingModule } from './route/service-routing.module';

@NgModule({
  imports: [SharedModule, ServiceRoutingModule],
  declarations: [ServiceComponent, ServiceDetailComponent, ServiceUpdateComponent, ServiceDeleteDialogComponent],
})
export class ServiceModule {}
