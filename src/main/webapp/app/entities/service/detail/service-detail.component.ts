import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IService } from '../service.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-service-detail',
  templateUrl: './service-detail.component.html',
})
export class ServiceDetailComponent implements OnInit {
  service: IService | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ service }) => {
      this.service = service;
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
