import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormationComponent } from '../list/formation.component';
import { FormationDetailComponent } from '../detail/formation-detail.component';
import { FormationUpdateComponent } from '../update/formation-update.component';
import { FormationRoutingResolveService } from './formation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const formationRoute: Routes = [
  {
    path: '',
    component: FormationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormationDetailComponent,
    resolve: {
      formation: FormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormationUpdateComponent,
    resolve: {
      formation: FormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormationUpdateComponent,
    resolve: {
      formation: FormationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formationRoute)],
  exports: [RouterModule],
})
export class FormationRoutingModule {}
