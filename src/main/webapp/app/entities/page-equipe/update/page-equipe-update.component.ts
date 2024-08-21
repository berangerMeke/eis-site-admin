import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PageEquipeFormService, PageEquipeFormGroup } from './page-equipe-form.service';
import { IPageEquipe } from '../page-equipe.model';
import { PageEquipeService } from '../service/page-equipe.service';

@Component({
  selector: 'jhi-page-equipe-update',
  templateUrl: './page-equipe-update.component.html',
})
export class PageEquipeUpdateComponent implements OnInit {
  isSaving = false;
  pageEquipe: IPageEquipe | null = null;

  editForm: PageEquipeFormGroup = this.pageEquipeFormService.createPageEquipeFormGroup();

  constructor(
    protected pageEquipeService: PageEquipeService,
    protected pageEquipeFormService: PageEquipeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageEquipe }) => {
      this.pageEquipe = pageEquipe;
      if (pageEquipe) {
        this.updateForm(pageEquipe);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pageEquipe = this.pageEquipeFormService.getPageEquipe(this.editForm);
    if (pageEquipe.id !== null) {
      this.subscribeToSaveResponse(this.pageEquipeService.update(pageEquipe));
    } else {
      this.subscribeToSaveResponse(this.pageEquipeService.create(pageEquipe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageEquipe>>): void {
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

  protected updateForm(pageEquipe: IPageEquipe): void {
    this.pageEquipe = pageEquipe;
    this.pageEquipeFormService.resetForm(this.editForm, pageEquipe);
  }
}
