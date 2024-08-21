import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageService } from '../page-service.model';

@Component({
  selector: 'jhi-page-service-detail',
  templateUrl: './page-service-detail.component.html',
})
export class PageServiceDetailComponent implements OnInit {
  pageService: IPageService | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageService }) => {
      this.pageService = pageService;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
