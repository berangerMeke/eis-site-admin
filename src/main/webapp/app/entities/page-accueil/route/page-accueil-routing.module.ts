import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PageAccueilComponent } from '../list/page-accueil.component';
import { PageAccueilDetailComponent } from '../detail/page-accueil-detail.component';
import { PageAccueilUpdateComponent } from '../update/page-accueil-update.component';
import { PageAccueilRoutingResolveService } from './page-accueil-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pageAccueilRoute: Routes = [
  {
    path: '',
    component: PageAccueilComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PageAccueilDetailComponent,
    resolve: {
      pageAccueil: PageAccueilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PageAccueilUpdateComponent,
    resolve: {
      pageAccueil: PageAccueilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PageAccueilUpdateComponent,
    resolve: {
      pageAccueil: PageAccueilRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pageAccueilRoute)],
  exports: [RouterModule],
})
export class PageAccueilRoutingModule {}
