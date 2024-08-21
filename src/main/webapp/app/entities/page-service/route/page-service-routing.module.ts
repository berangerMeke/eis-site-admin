import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PageServiceComponent } from '../list/page-service.component';
import { PageServiceDetailComponent } from '../detail/page-service-detail.component';
import { PageServiceUpdateComponent } from '../update/page-service-update.component';
import { PageServiceRoutingResolveService } from './page-service-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pageServiceRoute: Routes = [
  {
    path: '',
    component: PageServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PageServiceDetailComponent,
    resolve: {
      pageService: PageServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PageServiceUpdateComponent,
    resolve: {
      pageService: PageServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PageServiceUpdateComponent,
    resolve: {
      pageService: PageServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pageServiceRoute)],
  exports: [RouterModule],
})
export class PageServiceRoutingModule {}
