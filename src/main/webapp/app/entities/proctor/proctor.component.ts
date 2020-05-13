import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProctor } from 'app/shared/model/proctor.model';
import { ProctorService } from './proctor.service';
import { ProctorDeleteDialogComponent } from './proctor-delete-dialog.component';

@Component({
  selector: 'jhi-proctor',
  templateUrl: './proctor.component.html'
})
export class ProctorComponent implements OnInit, OnDestroy {
  proctors?: IProctor[];
  eventSubscriber?: Subscription;

  constructor(protected proctorService: ProctorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.proctorService.query().subscribe((res: HttpResponse<IProctor[]>) => (this.proctors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProctors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProctor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProctors(): void {
    this.eventSubscriber = this.eventManager.subscribe('proctorListModification', () => this.loadAll());
  }

  delete(proctor: IProctor): void {
    const modalRef = this.modalService.open(ProctorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proctor = proctor;
  }
}
