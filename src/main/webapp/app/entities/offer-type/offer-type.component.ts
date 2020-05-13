import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOfferType } from 'app/shared/model/offer-type.model';
import { OfferTypeService } from './offer-type.service';
import { OfferTypeDeleteDialogComponent } from './offer-type-delete-dialog.component';

@Component({
  selector: 'jhi-offer-type',
  templateUrl: './offer-type.component.html'
})
export class OfferTypeComponent implements OnInit, OnDestroy {
  offerTypes?: IOfferType[];
  eventSubscriber?: Subscription;

  constructor(protected offerTypeService: OfferTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.offerTypeService.query().subscribe((res: HttpResponse<IOfferType[]>) => (this.offerTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOfferTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOfferType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOfferTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('offerTypeListModification', () => this.loadAll());
  }

  delete(offerType: IOfferType): void {
    const modalRef = this.modalService.open(OfferTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.offerType = offerType;
  }
}
