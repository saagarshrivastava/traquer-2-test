import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';
import { ProctoringInstanceService } from './proctoring-instance.service';
import { ProctoringInstanceDeleteDialogComponent } from './proctoring-instance-delete-dialog.component';

@Component({
  selector: 'jhi-proctoring-instance',
  templateUrl: './proctoring-instance.component.html'
})
export class ProctoringInstanceComponent implements OnInit, OnDestroy {
  proctoringInstances?: IProctoringInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected proctoringInstanceService: ProctoringInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.proctoringInstanceService
      .query()
      .subscribe((res: HttpResponse<IProctoringInstance[]>) => (this.proctoringInstances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProctoringInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProctoringInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProctoringInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('proctoringInstanceListModification', () => this.loadAll());
  }

  delete(proctoringInstance: IProctoringInstance): void {
    const modalRef = this.modalService.open(ProctoringInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proctoringInstance = proctoringInstance;
  }
}
