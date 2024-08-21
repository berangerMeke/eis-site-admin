import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MembreEquipeComponent } from '../list/membre-equipe.component';
import { MembreEquipeDetailComponent } from '../detail/membre-equipe-detail.component';
import { MembreEquipeUpdateComponent } from '../update/membre-equipe-update.component';
import { MembreEquipeRoutingResolveService } from './membre-equipe-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const membreEquipeRoute: Routes = [
  {
    path: '',
    component: MembreEquipeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MembreEquipeDetailComponent,
    resolve: {
      membreEquipe: MembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MembreEquipeUpdateComponent,
    resolve: {
      membreEquipe: MembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MembreEquipeUpdateComponent,
    resolve: {
      membreEquipe: MembreEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(membreEquipeRoute)],
  exports: [RouterModule],
})
export class MembreEquipeRoutingModule {}
