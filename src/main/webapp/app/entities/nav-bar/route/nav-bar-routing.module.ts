import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NavBarComponent } from '../list/nav-bar.component';
import { NavBarDetailComponent } from '../detail/nav-bar-detail.component';
import { NavBarUpdateComponent } from '../update/nav-bar-update.component';
import { NavBarRoutingResolveService } from './nav-bar-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const navBarRoute: Routes = [
  {
    path: '',
    component: NavBarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NavBarDetailComponent,
    resolve: {
      navBar: NavBarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NavBarUpdateComponent,
    resolve: {
      navBar: NavBarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NavBarUpdateComponent,
    resolve: {
      navBar: NavBarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(navBarRoute)],
  exports: [RouterModule],
})
export class NavBarRoutingModule {}
