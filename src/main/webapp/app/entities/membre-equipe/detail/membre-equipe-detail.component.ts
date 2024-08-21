import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembreEquipe } from '../membre-equipe.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-membre-equipe-detail',
  templateUrl: './membre-equipe-detail.component.html',
})
export class MembreEquipeDetailComponent implements OnInit {
  membreEquipe: IMembreEquipe | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreEquipe }) => {
      this.membreEquipe = membreEquipe;
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
