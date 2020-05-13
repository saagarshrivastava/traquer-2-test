import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupportInstance } from 'app/shared/model/support-instance.model';
import { SupportInstanceService } from './support-instance.service';
import { SupportInstanceDeleteDialogComponent } from './support-instance-delete-dialog.component';

@Component({
  selector: 'jhi-support-instance',
  templateUrl: './support-instance.component.html'
})
export class SupportInstanceComponent implements OnInit, OnDestroy {
  supportInstances?: ISupportInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected supportInstanceService: SupportInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.supportInstanceService.query().subscribe((res: HttpResponse<ISupportInstance[]>) => (this.supportInstances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSupportInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISupportInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSupportInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('supportInstanceListModification', () => this.loadAll());
  }

  delete(supportInstance: ISupportInstance): void {
    const modalRef = this.modalService.open(SupportInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supportInstance = supportInstance;
  }
}
