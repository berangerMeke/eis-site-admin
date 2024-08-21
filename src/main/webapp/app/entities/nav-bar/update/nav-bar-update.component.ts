import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NavBarFormService, NavBarFormGroup } from './nav-bar-form.service';
import { INavBar } from '../nav-bar.model';
import { NavBarService } from '../service/nav-bar.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-nav-bar-update',
  templateUrl: './nav-bar-update.component.html',
})
export class NavBarUpdateComponent implements OnInit {
  isSaving = false;
  navBar: INavBar | null = null;

  editForm: NavBarFormGroup = this.navBarFormService.createNavBarFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected navBarService: NavBarService,
    protected navBarFormService: NavBarFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ navBar }) => {
      this.navBar = navBar;
      if (navBar) {
        this.updateForm(navBar);
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
    const navBar = this.navBarFormService.getNavBar(this.editForm);
    if (navBar.id !== null) {
      this.subscribeToSaveResponse(this.navBarService.update(navBar));
    } else {
      this.subscribeToSaveResponse(this.navBarService.create(navBar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INavBar>>): void {
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

  protected updateForm(navBar: INavBar): void {
    this.navBar = navBar;
    this.navBarFormService.resetForm(this.editForm, navBar);
  }
}
