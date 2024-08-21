import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PageEquipeComponent } from '../list/page-equipe.component';
import { PageEquipeDetailComponent } from '../detail/page-equipe-detail.component';
import { PageEquipeUpdateComponent } from '../update/page-equipe-update.component';
import { PageEquipeRoutingResolveService } from './page-equipe-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pageEquipeRoute: Routes = [
  {
    path: '',
    component: PageEquipeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PageEquipeDetailComponent,
    resolve: {
      pageEquipe: PageEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PageEquipeUpdateComponent,
    resolve: {
      pageEquipe: PageEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PageEquipeUpdateComponent,
    resolve: {
      pageEquipe: PageEquipeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pageEquipeRoute)],
  exports: [RouterModule],
})
export class PageEquipeRoutingModule {}
