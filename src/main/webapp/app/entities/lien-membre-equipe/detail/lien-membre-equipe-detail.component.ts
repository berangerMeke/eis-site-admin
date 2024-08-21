import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILienMembreEquipe } from '../lien-membre-equipe.model';

@Component({
  selector: 'jhi-lien-membre-equipe-detail',
  templateUrl: './lien-membre-equipe-detail.component.html',
})
export class LienMembreEquipeDetailComponent implements OnInit {
  lienMembreEquipe: ILienMembreEquipe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lienMembreEquipe }) => {
      this.lienMembreEquipe = lienMembreEquipe;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
