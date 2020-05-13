import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from './delivery-status.service';
import { DeliveryStatusDeleteDialogComponent } from './delivery-status-delete-dialog.component';

@Component({
  selector: 'jhi-delivery-status',
  templateUrl: './delivery-status.component.html'
})
export class DeliveryStatusComponent implements OnInit, OnDestroy {
  deliveryStatuses?: IDeliveryStatus[];
  eventSubscriber?: Subscription;

  constructor(
    protected deliveryStatusService: DeliveryStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.deliveryStatusService.query().subscribe((res: HttpResponse<IDeliveryStatus[]>) => (this.deliveryStatuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeliveryStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeliveryStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeliveryStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('deliveryStatusListModification', () => this.loadAll());
  }

  delete(deliveryStatus: IDeliveryStatus): void {
    const modalRef = this.modalService.open(DeliveryStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deliveryStatus = deliveryStatus;
  }
}
