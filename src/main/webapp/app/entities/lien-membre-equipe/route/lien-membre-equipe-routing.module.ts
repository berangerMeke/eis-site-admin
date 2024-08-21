import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LienMembreEquipeComponent } from '../list/lien-membre-equipe.component';
import { LienMembreEquipeDetailComponent } from '../detail/lien-membre-equipe-detail.component';
import { LienMembreEquipeUpdateComponent } from '../update/lien-membre-equipe-update.component';
import { LienMembreEquipeRoutingResolveService } from './lien-membre-equipe-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const lienMembreEquipeRoute: Routes = [
  {
    path: '',
    component: LienMembreEquipeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LienMembreEquipeDetailComponent,
    resolve: {
      lienMembreEquipe: LienMembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LienMembreEquipeUpdateComponent,
    resolve: {
      lienMembreEquipe: LienMembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LienMembreEquipeUpdateComponent,
    resolve: {
      lienMembreEquipe: LienMembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lienMembreEquipeRoute)],
  exports: [RouterModule],
})
export class LienMembreEquipeRoutingModule {}
