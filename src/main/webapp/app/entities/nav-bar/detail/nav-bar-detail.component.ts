import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INavBar } from '../nav-bar.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-nav-bar-detail',
  templateUrl: './nav-bar-detail.component.html',
})
export class NavBarDetailComponent implements OnInit {
  navBar: INavBar | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ navBar }) => {
      this.navBar = navBar;
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
