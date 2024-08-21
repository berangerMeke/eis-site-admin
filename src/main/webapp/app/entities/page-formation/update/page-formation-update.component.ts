import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PageFormationFormService, PageFormationFormGroup } from './page-formation-form.service';
import { IPageFormation } from '../page-formation.model';
import { PageFormationService } from '../service/page-formation.service';

@Component({
  selector: 'jhi-page-formation-update',
  templateUrl: './page-formation-update.component.html',
})
export class PageFormationUpdateComponent implements OnInit {
  isSaving = false;
  pageFormation: IPageFormation | null = null;

  editForm: PageFormationFormGroup = this.pageFormationFormService.createPageFormationFormGroup();

  constructor(
    protected pageFormationService: PageFormationService,
    protected pageFormationFormService: PageFormationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageFormation }) => {
      this.pageFormation = pageFormation;
      if (pageFormation) {
        this.updateForm(pageFormation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pageFormation = this.pageFormationFormService.getPageFormation(this.editForm);
    if (pageFormation.id !== null) {
      this.subscribeToSaveResponse(this.pageFormationService.update(pageFormation));
    } else {
      this.subscribeToSaveResponse(this.pageFormationService.create(pageFormation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageFormation>>): void {
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

  protected updateForm(pageFormation: IPageFormation): void {
    this.pageFormation = pageFormation;
    this.pageFormationFormService.resetForm(this.editForm, pageFormation);
  }
}
