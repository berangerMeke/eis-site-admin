import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartenairesComponent } from '../list/partenaires.component';
import { PartenairesDetailComponent } from '../detail/partenaires-detail.component';
import { PartenairesUpdateComponent } from '../update/partenaires-update.component';
import { PartenairesRoutingResolveService } from './partenaires-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const partenairesRoute: Routes = [
  {
    path: '',
    component: PartenairesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartenairesDetailComponent,
    resolve: {
      partenaires: PartenairesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartenairesUpdateComponent,
    resolve: {
      partenaires: PartenairesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartenairesUpdateComponent,
    resolve: {
      partenaires: PartenairesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partenairesRoute)],
  exports: [RouterModule],
})
export class PartenairesRoutingModule {}
