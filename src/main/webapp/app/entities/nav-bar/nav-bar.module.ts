import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NavBarComponent } from './list/nav-bar.component';
import { NavBarDetailComponent } from './detail/nav-bar-detail.component';
import { NavBarUpdateComponent } from './update/nav-bar-update.component';
import { NavBarDeleteDialogComponent } from './delete/nav-bar-delete-dialog.component';
import { NavBarRoutingModule } from './route/nav-bar-routing.module';

@NgModule({
  imports: [SharedModule, NavBarRoutingModule],
  declarations: [NavBarComponent, NavBarDetailComponent, NavBarUpdateComponent, NavBarDeleteDialogComponent],
})
export class NavBarModule {}
