import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubcategoryInstance } from 'app/shared/model/subcategory-instance.model';
import { SubcategoryInstanceService } from './subcategory-instance.service';
import { SubcategoryInstanceDeleteDialogComponent } from './subcategory-instance-delete-dialog.component';

@Component({
  selector: 'jhi-subcategory-instance',
  templateUrl: './subcategory-instance.component.html'
})
export class SubcategoryInstanceComponent implements OnInit, OnDestroy {
  subcategoryInstances?: ISubcategoryInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected subcategoryInstanceService: SubcategoryInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.subcategoryInstanceService
      .query()
      .subscribe((res: HttpResponse<ISubcategoryInstance[]>) => (this.subcategoryInstances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubcategoryInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubcategoryInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubcategoryInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('subcategoryInstanceListModification', () => this.loadAll());
  }

  delete(subcategoryInstance: ISubcategoryInstance): void {
    const modalRef = this.modalService.open(SubcategoryInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subcategoryInstance = subcategoryInstance;
  }
}
