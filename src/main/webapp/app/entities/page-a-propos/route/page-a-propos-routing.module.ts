import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PageAProposComponent } from '../list/page-a-propos.component';
import { PageAProposDetailComponent } from '../detail/page-a-propos-detail.component';
import { PageAProposUpdateComponent } from '../update/page-a-propos-update.component';
import { PageAProposRoutingResolveService } from './page-a-propos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pageAProposRoute: Routes = [
  {
    path: '',
    component: PageAProposComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PageAProposDetailComponent,
    resolve: {
      pageAPropos: PageAProposRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PageAProposUpdateComponent,
    resolve: {
      pageAPropos: PageAProposRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PageAProposUpdateComponent,
    resolve: {
      pageAPropos: PageAProposRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pageAProposRoute)],
  exports: [RouterModule],
})
export class PageAProposRoutingModule {}
