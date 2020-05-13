import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from './exam-backend.service';
import { ExamBackendDeleteDialogComponent } from './exam-backend-delete-dialog.component';

@Component({
  selector: 'jhi-exam-backend',
  templateUrl: './exam-backend.component.html'
})
export class ExamBackendComponent implements OnInit, OnDestroy {
  examBackends?: IExamBackend[];
  eventSubscriber?: Subscription;

  constructor(
    protected examBackendService: ExamBackendService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.examBackendService.query().subscribe((res: HttpResponse<IExamBackend[]>) => (this.examBackends = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExamBackends();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExamBackend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExamBackends(): void {
    this.eventSubscriber = this.eventManager.subscribe('examBackendListModification', () => this.loadAll());
  }

  delete(examBackend: IExamBackend): void {
    const modalRef = this.modalService.open(ExamBackendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examBackend = examBackend;
  }
}
