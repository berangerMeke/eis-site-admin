import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartenaires } from '../partenaires.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-partenaires-detail',
  templateUrl: './partenaires-detail.component.html',
})
export class PartenairesDetailComponent implements OnInit {
  partenaires: IPartenaires | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partenaires }) => {
      this.partenaires = partenaires;
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
