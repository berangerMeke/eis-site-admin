import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LienMembreEquipeFormService, LienMembreEquipeFormGroup } from './lien-membre-equipe-form.service';
import { ILienMembreEquipe } from '../lien-membre-equipe.model';
import { LienMembreEquipeService } from '../service/lien-membre-equipe.service';

@Component({
  selector: 'jhi-lien-membre-equipe-update',
  templateUrl: './lien-membre-equipe-update.component.html',
})
export class LienMembreEquipeUpdateComponent implements OnInit {
  isSaving = false;
  lienMembreEquipe: ILienMembreEquipe | null = null;

  editForm: LienMembreEquipeFormGroup = this.lienMembreEquipeFormService.createLienMembreEquipeFormGroup();

  constructor(
    protected lienMembreEquipeService: LienMembreEquipeService,
    protected lienMembreEquipeFormService: LienMembreEquipeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lienMembreEquipe }) => {
      this.lienMembreEquipe = lienMembreEquipe;
      if (lienMembreEquipe) {
        this.updateForm(lienMembreEquipe);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lienMembreEquipe = this.lienMembreEquipeFormService.getLienMembreEquipe(this.editForm);
    if (lienMembreEquipe.id !== null) {
      this.subscribeToSaveResponse(this.lienMembreEquipeService.update(lienMembreEquipe));
    } else {
      this.subscribeToSaveResponse(this.lienMembreEquipeService.create(lienMembreEquipe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILienMembreEquipe>>): void {
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

  protected updateForm(lienMembreEquipe: ILienMembreEquipe): void {
    this.lienMembreEquipe = lienMembreEquipe;
    this.lienMembreEquipeFormService.resetForm(this.editForm, lienMembreEquipe);
  }
}
