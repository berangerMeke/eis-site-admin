import { Component, OnInit, HostListener } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TransfertDataService } from 'app/utile/transfert-data.service';
import { PartenairesService } from '../../partenaires/service/partenaires.service';

import { IPartenaires } from '../../partenaires/partenaires.model';
import { IPageAccueil } from '../page-accueil.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, PageAccueilService } from '../service/page-accueil.service';
import { PageAccueilDeleteDialogComponent } from '../delete/page-accueil-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import { SortService } from 'app/shared/sort/sort.service';



interface Partner {
  logo: string;
  name: string;
  description: string;
}


@Component({
  selector: 'jhi-page-accueil',
  templateUrl: './page-accueil.component.html',
  styleUrls: ['./page-accueil.component.css'],
})
export class PageAccueilComponent implements OnInit {
  pageAccueils?: IPageAccueil[];
  partenaires?: IPartenaires[];
  isLoading = false;

  champVide = 'Entrer un contenu';

  predicate = 'id';
  ascending = true;

  constructor(
    protected pageAccueilService: PageAccueilService,
    protected partenairesService: PartenairesService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal,
    private transfertDataService: TransfertDataService,
  ) {}

  trackId = (_index: number, item: IPageAccueil): number => this.pageAccueilService.getPageAccueilIdentifier(item);

  ngOnInit(): void {
    this.loadPartenaires();
    this.load();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(pageAccueil: IPageAccueil): void {
    const modalRef = this.modalService.open(PageAccueilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pageAccueil = pageAccueil;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
       // this.loadPartenaires();
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.pageAccueils = this.refineData(dataFromBody);
  }

  protected refineData(data: IPageAccueil[]): IPageAccueil[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IPageAccueil[] | null): IPageAccueil[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.pageAccueilService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }


















  public section1Element(elt : string): void{
    localStorage.setItem("choixElement", elt);
    this.router.navigate(['/page-accueil', 1, 'edit']);
  }

  public section2Element(elt : string): void{
    localStorage.setItem("choixElement", elt);
    this.router.navigate(['/page-accueil', 1, 'edit']);
  }

  public sectionPartenaire(){
    this.router.navigate(['/partenaires'])
  }

  
  loadPartenaires(): void {
    this.partenairesService.query().subscribe({
      next: (res: EntityArrayResponseType) => {
        console.log(res);
        this.onResponseSuccessPartenaires(res);
      },
    })
  };

  protected refineDataPartenaires(data: IPartenaires[]): IPartenaires[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBodyPartenaires(data: IPartenaires[] | null): IPartenaires[] {
    return data ?? [];
  }

  protected onResponseSuccessPartenaires(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBodyPartenaires(response.body);
    this.partenaires = this.refineDataPartenaires(dataFromBody);
    console.log(this.partenaires);
  }





























  public title = "Titre";
  public showBlocks = {
    R1A: false,
    R1B: false,
    R1C: false,
    R2C:false,
    R2A: false,
    R2B: false,
    R3A: false,
    R3B: false,
    R4B: false,
    R3C: false,
    R4C: false,
    P: false
  };
  public blog: any[] = [ 
    

    {
      image:"expertise.png",

      titre:"Expertise Technique",
  
    },


    {
      image:"approche.png",

      titre:"Approche Personnalisee",
    
    },



    {
      image:"innovation (2).png",

      titre:"Innovation et Veille Technologique",


      paragraphe:""
    
    
    },




    {
      image:"collaboration.png",

      titre:"Collaboration Active",


      paragraphe:" "
    
    
    },




    {
      image:"design.png",

      titre:"Design Esthetique",


      paragraphe:" "
    
    
    },





    {
      image:"reactivite.png",

      titre:"Reactivite et Flexibilite",


      paragraphe:" "
    
    
    },

    {
      image:"gestion.png",
      titre:"Gestion de Projet Transparente",
      paragraphe: "",
    }

  
  ];

  @HostListener('window:scroll', [])
  onWindowScroll() {
    this.checkBlocksVisibility();
  }

  ngAfterViewInit() {
    this.checkBlocksVisibility(); 
    window.addEventListener('resize', () => this.checkBlocksVisibility()); 
  }

  private checkBlocksVisibility() {
    this.showBlocks.R1A = this.isVisible('.R1A');
    this.showBlocks.R1B = this.isVisible('.R1B');
    this.showBlocks.R1C = this.isVisible('.R1C');
    this.showBlocks.R2A = this.isVisible('.R2A');
    this.showBlocks.R2B = this.isVisible('.R2B');
    this.showBlocks.R3A = this.isVisible('.R3A');
    this.showBlocks.R3B = this.isVisible('.R3B');
    this.showBlocks.R4B = this.isVisible('.R4B');
    this.showBlocks.R2C = this.isVisible('.R2C');
    this.showBlocks.R3C = this.isVisible('.R3C');
    this.showBlocks.R4C = this.isVisible('.R4C');
    this.showBlocks.P = this.isVisible('.P');
  }

  private isVisible(selector: string): boolean {
    const element = document.querySelector(selector);
    return element ? element.getBoundingClientRect().top < window.innerHeight && element.getBoundingClientRect().bottom > 0 : false;
  }
  public partners: Partner[] = [

    {
      logo: 'FaculteA.png',
      name: 'Faculté des sciences',
      description: 'EIS-Innovation une équipe engagée.'
    },

    {
      logo: 'AfriqueA.png',
      name: 'ADI',
      description: 'EIS-Innovation une équipe engagée.'
    },

    {
      logo: 'challenge.png',
      name: 'Challenge Intelligence',
      description: 'EIS-Innovation une équipe engagée.'
    },

    {
      logo: 'MA64.jpg',
      name: 'MA64',
      description: 'EIS-Innovation une équipe engagée'
    },


  ];






}
