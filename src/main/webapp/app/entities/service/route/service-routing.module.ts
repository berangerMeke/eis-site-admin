import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceComponent } from '../list/service.component';
import { ServiceDetailComponent } from '../detail/service-detail.component';
import { ServiceUpdateComponent } from '../update/service-update.component';
import { ServiceRoutingResolveService } from './service-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const serviceRoute: Routes = [
  {
    path: '',
    component: ServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceDetailComponent,
    resolve: {
      service: ServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceUpdateComponent,
    resolve: {
      service: ServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceRoute)],
  exports: [RouterModule],
})
export class ServiceRoutingModule {}
