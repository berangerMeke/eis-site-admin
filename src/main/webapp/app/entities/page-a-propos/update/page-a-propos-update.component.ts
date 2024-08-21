import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PageAProposFormService, PageAProposFormGroup } from './page-a-propos-form.service';
import { IPageAPropos } from '../page-a-propos.model';
import { PageAProposService } from '../service/page-a-propos.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IMembreEquipe } from 'app/entities/membre-equipe/membre-equipe.model';
import { MembreEquipeService } from 'app/entities/membre-equipe/service/membre-equipe.service';

@Component({
  selector: 'jhi-page-a-propos-update',
  templateUrl: './page-a-propos-update.component.html',
})
export class PageAProposUpdateComponent implements OnInit {
  isSaving = false;
  pageAPropos: IPageAPropos | null = null;

  membreEquipesSharedCollection: IMembreEquipe[] = [];

  editForm: PageAProposFormGroup = this.pageAProposFormService.createPageAProposFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pageAProposService: PageAProposService,
    protected pageAProposFormService: PageAProposFormService,
    protected membreEquipeService: MembreEquipeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMembreEquipe = (o1: IMembreEquipe | null, o2: IMembreEquipe | null): boolean =>
    this.membreEquipeService.compareMembreEquipe(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageAPropos }) => {
      this.pageAPropos = pageAPropos;
      if (pageAPropos) {
        this.updateForm(pageAPropos);
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
    const pageAPropos = this.pageAProposFormService.getPageAPropos(this.editForm);
    if (pageAPropos.id !== null) {
      this.subscribeToSaveResponse(this.pageAProposService.update(pageAPropos));
    } else {
      this.subscribeToSaveResponse(this.pageAProposService.create(pageAPropos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageAPropos>>): void {
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

  protected updateForm(pageAPropos: IPageAPropos): void {
    this.pageAPropos = pageAPropos;
    this.pageAProposFormService.resetForm(this.editForm, pageAPropos);

    this.membreEquipesSharedCollection = this.membreEquipeService.addMembreEquipeToCollectionIfMissing<IMembreEquipe>(
      this.membreEquipesSharedCollection,
      pageAPropos.membreEquipe1
    );
  }

  protected loadRelationshipsOptions(): void {
    this.membreEquipeService
      .query()
      .pipe(map((res: HttpResponse<IMembreEquipe[]>) => res.body ?? []))
      .pipe(
        map((membreEquipes: IMembreEquipe[]) =>
          this.membreEquipeService.addMembreEquipeToCollectionIfMissing<IMembreEquipe>(membreEquipes, this.pageAPropos?.membreEquipe1)
        )
      )
      .subscribe((membreEquipes: IMembreEquipe[]) => (this.membreEquipesSharedCollection = membreEquipes));
  }
}
