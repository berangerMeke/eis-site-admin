import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageFormation } from '../page-formation.model';

@Component({
  selector: 'jhi-page-formation-detail',
  templateUrl: './page-formation-detail.component.html',
})
export class PageFormationDetailComponent implements OnInit {
  pageFormation: IPageFormation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageFormation }) => {
      this.pageFormation = pageFormation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
