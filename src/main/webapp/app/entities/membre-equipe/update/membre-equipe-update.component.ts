import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MembreEquipeFormService, MembreEquipeFormGroup } from './membre-equipe-form.service';
import { IMembreEquipe } from '../membre-equipe.model';
import { MembreEquipeService } from '../service/membre-equipe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPageEquipe } from 'app/entities/page-equipe/page-equipe.model';
import { PageEquipeService } from 'app/entities/page-equipe/service/page-equipe.service';

@Component({
  selector: 'jhi-membre-equipe-update',
  templateUrl: './membre-equipe-update.component.html',
})
export class MembreEquipeUpdateComponent implements OnInit {
  isSaving = false;
  membreEquipe: IMembreEquipe | null = null;

  pageEquipesSharedCollection: IPageEquipe[] = [];

  editForm: MembreEquipeFormGroup = this.membreEquipeFormService.createMembreEquipeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected membreEquipeService: MembreEquipeService,
    protected membreEquipeFormService: MembreEquipeFormService,
    protected pageEquipeService: PageEquipeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePageEquipe = (o1: IPageEquipe | null, o2: IPageEquipe | null): boolean => this.pageEquipeService.comparePageEquipe(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreEquipe }) => {
      this.membreEquipe = membreEquipe;
      if (membreEquipe) {
        this.updateForm(membreEquipe);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('siteEisApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membreEquipe = this.membreEquipeFormService.getMembreEquipe(this.editForm);
    if (membreEquipe.id !== null) {
      this.subscribeToSaveResponse(this.membreEquipeService.update(membreEquipe));
    } else {
      this.subscribeToSaveResponse(this.membreEquipeService.create(membreEquipe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreEquipe>>): void {
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

  protected updateForm(membreEquipe: IMembreEquipe): void {
    this.membreEquipe = membreEquipe;
    this.membreEquipeFormService.resetForm(this.editForm, membreEquipe);

    this.pageEquipesSharedCollection = this.pageEquipeService.addPageEquipeToCollectionIfMissing<IPageEquipe>(
      this.pageEquipesSharedCollection,
      membreEquipe.pageEquipe1
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pageEquipeService
      .query()
      .pipe(map((res: HttpResponse<IPageEquipe[]>) => res.body ?? []))
      .pipe(
        map((pageEquipes: IPageEquipe[]) =>
          this.pageEquipeService.addPageEquipeToCollectionIfMissing<IPageEquipe>(pageEquipes, this.membreEquipe?.pageEquipe1)
        )
      )
      .subscribe((pageEquipes: IPageEquipe[]) => (this.pageEquipesSharedCollection = pageEquipes));
  }
}
