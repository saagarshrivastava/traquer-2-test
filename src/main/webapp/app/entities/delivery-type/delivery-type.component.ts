import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliveryType } from 'app/shared/model/delivery-type.model';
import { DeliveryTypeService } from './delivery-type.service';
import { DeliveryTypeDeleteDialogComponent } from './delivery-type-delete-dialog.component';

@Component({
  selector: 'jhi-delivery-type',
  templateUrl: './delivery-type.component.html'
})
export class DeliveryTypeComponent implements OnInit, OnDestroy {
  deliveryTypes?: IDeliveryType[];
  eventSubscriber?: Subscription;

  constructor(
    protected deliveryTypeService: DeliveryTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.deliveryTypeService.query().subscribe((res: HttpResponse<IDeliveryType[]>) => (this.deliveryTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeliveryTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeliveryType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeliveryTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('deliveryTypeListModification', () => this.loadAll());
  }

  delete(deliveryType: IDeliveryType): void {
    const modalRef = this.modalService.open(DeliveryTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deliveryType = deliveryType;
  }
}
