import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from './category-instance.service';
import { CategoryInstanceDeleteDialogComponent } from './category-instance-delete-dialog.component';

@Component({
  selector: 'jhi-category-instance',
  templateUrl: './category-instance.component.html'
})
export class CategoryInstanceComponent implements OnInit, OnDestroy {
  categoryInstances?: ICategoryInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected categoryInstanceService: CategoryInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.categoryInstanceService.query().subscribe((res: HttpResponse<ICategoryInstance[]>) => (this.categoryInstances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategoryInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategoryInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategoryInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoryInstanceListModification', () => this.loadAll());
  }

  delete(categoryInstance: ICategoryInstance): void {
    const modalRef = this.modalService.open(CategoryInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoryInstance = categoryInstance;
  }
}
