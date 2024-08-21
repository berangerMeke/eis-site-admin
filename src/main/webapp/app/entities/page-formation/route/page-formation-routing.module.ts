import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PageFormationComponent } from '../list/page-formation.component';
import { PageFormationDetailComponent } from '../detail/page-formation-detail.component';
import { PageFormationUpdateComponent } from '../update/page-formation-update.component';
import { PageFormationRoutingResolveService } from './page-formation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pageFormationRoute: Routes = [
  {
    path: '',
    component: PageFormationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PageFormationDetailComponent,
    resolve: {
      pageFormation: PageFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PageFormationUpdateComponent,
    resolve: {
      pageFormation: PageFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PageFormationUpdateComponent,
    resolve: {
      pageFormation: PageFormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pageFormationRoute)],
  exports: [RouterModule],
})
export class PageFormationRoutingModule {}
