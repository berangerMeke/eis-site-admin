import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'formation',
        data: { pageTitle: 'siteEisApp.formation.home.title' },
        loadChildren: () => import('./formation/formation.module').then(m => m.FormationModule),
      },
      {
        path: 'service',
        data: { pageTitle: 'siteEisApp.service.home.title' },
        loadChildren: () => import('./service/service.module').then(m => m.ServiceModule),
      },
      {
        path: 'partenaires',
        data: { pageTitle: 'siteEisApp.partenaires.home.title' },
        loadChildren: () => import('./partenaires/partenaires.module').then(m => m.PartenairesModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'siteEisApp.contact.home.title' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'lien-membre-equipe',
        data: { pageTitle: 'siteEisApp.lienMembreEquipe.home.title' },
        loadChildren: () => import('./lien-membre-equipe/lien-membre-equipe.module').then(m => m.LienMembreEquipeModule),
      },
      {
        path: 'membre-equipe',
        data: { pageTitle: 'siteEisApp.membreEquipe.home.title' },
        loadChildren: () => import('./membre-equipe/membre-equipe.module').then(m => m.MembreEquipeModule),
      },
      {
        path: 'page-service',
        data: { pageTitle: 'siteEisApp.pageService.home.title' },
        loadChildren: () => import('./page-service/page-service.module').then(m => m.PageServiceModule),
      },
      {
        path: 'page-formation',
        data: { pageTitle: 'siteEisApp.pageFormation.home.title' },
        loadChildren: () => import('./page-formation/page-formation.module').then(m => m.PageFormationModule),
      },
      {
        path: 'page-equipe',
        data: { pageTitle: 'siteEisApp.pageEquipe.home.title' },
        loadChildren: () => import('./page-equipe/page-equipe.module').then(m => m.PageEquipeModule),
      },
      {
        path: 'nav-bar',
        data: { pageTitle: 'siteEisApp.navBar.home.title' },
        loadChildren: () => import('./nav-bar/nav-bar.module').then(m => m.NavBarModule),
      },
      {
        path: 'page-a-propos',
        data: { pageTitle: 'siteEisApp.pageAPropos.home.title' },
        loadChildren: () => import('./page-a-propos/page-a-propos.module').then(m => m.PageAProposModule),
      },
      {
        path: 'page-accueil',
        data: { pageTitle: 'siteEisApp.pageAccueil.home.title' },
        loadChildren: () => import('./page-accueil/page-accueil.module').then(m => m.PageAccueilModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
