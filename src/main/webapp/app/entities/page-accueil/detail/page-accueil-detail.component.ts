import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageAccueil } from '../page-accueil.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-page-accueil-detail',
  templateUrl: './page-accueil-detail.component.html',
})
export class PageAccueilDetailComponent implements OnInit {
  pageAccueil: IPageAccueil | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageAccueil }) => {
      this.pageAccueil = pageAccueil;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
