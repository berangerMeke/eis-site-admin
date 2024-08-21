import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PageAccueilFormService, PageAccueilFormGroup } from './page-accueil-form.service';
import { IPageAccueil } from '../page-accueil.model';
import { PageAccueilService } from '../service/page-accueil.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPartenaires } from 'app/entities/partenaires/partenaires.model';
import { PartenairesService } from 'app/entities/partenaires/service/partenaires.service';

@Component({
  selector: 'jhi-page-accueil-update',
  templateUrl: './page-accueil-update.component.html',
})
export class PageAccueilUpdateComponent implements OnInit {
  isSaving = false;
  pageAccueil: IPageAccueil | null = null;

  partenairesSharedCollection: IPartenaires[] = [];

  editForm: PageAccueilFormGroup = this.pageAccueilFormService.createPageAccueilFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pageAccueilService: PageAccueilService,
    protected pageAccueilFormService: PageAccueilFormService,
    protected partenairesService: PartenairesService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePartenaires = (o1: IPartenaires | null, o2: IPartenaires | null): boolean => this.partenairesService.comparePartenaires(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageAccueil }) => {
      this.pageAccueil = pageAccueil;
      if (pageAccueil) {
        this.updateForm(pageAccueil);
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
    const pageAccueil = this.pageAccueilFormService.getPageAccueil(this.editForm);
    if (pageAccueil.id !== null) {
      this.subscribeToSaveResponse(this.pageAccueilService.update(pageAccueil));
    } else {
      this.subscribeToSaveResponse(this.pageAccueilService.create(pageAccueil));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageAccueil>>): void {
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

  protected updateForm(pageAccueil: IPageAccueil): void {
    this.pageAccueil = pageAccueil;
    this.pageAccueilFormService.resetForm(this.editForm, pageAccueil);

    this.partenairesSharedCollection = this.partenairesService.addPartenairesToCollectionIfMissing<IPartenaires>(
      this.partenairesSharedCollection,
      pageAccueil.partenaires1
    );
  }

  protected loadRelationshipsOptions(): void {
    this.partenairesService
      .query()
      .pipe(map((res: HttpResponse<IPartenaires[]>) => res.body ?? []))
      .pipe(
        map((partenaires: IPartenaires[]) =>
          this.partenairesService.addPartenairesToCollectionIfMissing<IPartenaires>(partenaires, this.pageAccueil?.partenaires1)
        )
      )
      .subscribe((partenaires: IPartenaires[]) => (this.partenairesSharedCollection = partenaires));
  }
}
