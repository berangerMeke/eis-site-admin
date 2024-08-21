import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PartenairesFormService, PartenairesFormGroup } from './partenaires-form.service';
import { IPartenaires } from '../partenaires.model';
import { PartenairesService } from '../service/partenaires.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-partenaires-update',
  templateUrl: './partenaires-update.component.html',
})
export class PartenairesUpdateComponent implements OnInit {
  isSaving = false;
  partenaires: IPartenaires | null = null;

  editForm: PartenairesFormGroup = this.partenairesFormService.createPartenairesFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected partenairesService: PartenairesService,
    protected partenairesFormService: PartenairesFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partenaires }) => {
      this.partenaires = partenaires;
      if (partenaires) {
        this.updateForm(partenaires);
      }
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
    const partenaires = this.partenairesFormService.getPartenaires(this.editForm);
    if (partenaires.id !== null) {
      this.subscribeToSaveResponse(this.partenairesService.update(partenaires));
    } else {
      this.subscribeToSaveResponse(this.partenairesService.create(partenaires));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartenaires>>): void {
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

  protected updateForm(partenaires: IPartenaires): void {
    this.partenaires = partenaires;
    this.partenairesFormService.resetForm(this.editForm, partenaires);
  }
}
