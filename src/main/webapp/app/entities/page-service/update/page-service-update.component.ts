import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PageServiceFormService, PageServiceFormGroup } from './page-service-form.service';
import { IPageService } from '../page-service.model';
import { PageServiceService } from '../service/page-service.service';

@Component({
  selector: 'jhi-page-service-update',
  templateUrl: './page-service-update.component.html',
})
export class PageServiceUpdateComponent implements OnInit {
  isSaving = false;
  pageService: IPageService | null = null;

  editForm: PageServiceFormGroup = this.pageServiceFormService.createPageServiceFormGroup();

  constructor(
    protected pageServiceService: PageServiceService,
    protected pageServiceFormService: PageServiceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageService }) => {
      this.pageService = pageService;
      if (pageService) {
        this.updateForm(pageService);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pageService = this.pageServiceFormService.getPageService(this.editForm);
    if (pageService.id !== null) {
      this.subscribeToSaveResponse(this.pageServiceService.update(pageService));
    } else {
      this.subscribeToSaveResponse(this.pageServiceService.create(pageService));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageService>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pageService: IPageService): void {
    this.pageService = pageService;
    this.pageServiceFormService.resetForm(this.editForm, pageService);
  }
}
