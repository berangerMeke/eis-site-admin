import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageEquipe } from '../page-equipe.model';

@Component({
  selector: 'jhi-page-equipe-detail',
  templateUrl: './page-equipe-detail.component.html',
})
export class PageEquipeDetailComponent implements OnInit {
  pageEquipe: IPageEquipe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageEquipe }) => {
      this.pageEquipe = pageEquipe;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
