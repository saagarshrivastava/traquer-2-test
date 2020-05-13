import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupportPerson } from 'app/shared/model/support-person.model';
import { SupportPersonService } from './support-person.service';
import { SupportPersonDeleteDialogComponent } from './support-person-delete-dialog.component';

@Component({
  selector: 'jhi-support-person',
  templateUrl: './support-person.component.html'
})
export class SupportPersonComponent implements OnInit, OnDestroy {
  supportPeople?: ISupportPerson[];
  eventSubscriber?: Subscription;

  constructor(
    protected supportPersonService: SupportPersonService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.supportPersonService.query().subscribe((res: HttpResponse<ISupportPerson[]>) => (this.supportPeople = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSupportPeople();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISupportPerson): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSupportPeople(): void {
    this.eventSubscriber = this.eventManager.subscribe('supportPersonListModification', () => this.loadAll());
  }

  delete(supportPerson: ISupportPerson): void {
    const modalRef = this.modalService.open(SupportPersonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supportPerson = supportPerson;
  }
}
