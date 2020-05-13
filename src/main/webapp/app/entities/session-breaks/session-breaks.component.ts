import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISessionBreaks } from 'app/shared/model/session-breaks.model';
import { SessionBreaksService } from './session-breaks.service';
import { SessionBreaksDeleteDialogComponent } from './session-breaks-delete-dialog.component';

@Component({
  selector: 'jhi-session-breaks',
  templateUrl: './session-breaks.component.html'
})
export class SessionBreaksComponent implements OnInit, OnDestroy {
  sessionBreaks?: ISessionBreaks[];
  eventSubscriber?: Subscription;

  constructor(
    protected sessionBreaksService: SessionBreaksService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.sessionBreaksService.query().subscribe((res: HttpResponse<ISessionBreaks[]>) => (this.sessionBreaks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSessionBreaks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISessionBreaks): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSessionBreaks(): void {
    this.eventSubscriber = this.eventManager.subscribe('sessionBreaksListModification', () => this.loadAll());
  }

  delete(sessionBreaks: ISessionBreaks): void {
    const modalRef = this.modalService.open(SessionBreaksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sessionBreaks = sessionBreaks;
  }
}
