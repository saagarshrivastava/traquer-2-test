import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from './subcategory.service';
import { SubcategoryDeleteDialogComponent } from './subcategory-delete-dialog.component';

@Component({
  selector: 'jhi-subcategory',
  templateUrl: './subcategory.component.html'
})
export class SubcategoryComponent implements OnInit, OnDestroy {
  subcategories?: ISubcategory[];
  eventSubscriber?: Subscription;

  constructor(
    protected subcategoryService: SubcategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.subcategoryService.query().subscribe((res: HttpResponse<ISubcategory[]>) => (this.subcategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubcategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubcategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubcategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('subcategoryListModification', () => this.loadAll());
  }

  delete(subcategory: ISubcategory): void {
    const modalRef = this.modalService.open(SubcategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subcategory = subcategory;
  }
}
