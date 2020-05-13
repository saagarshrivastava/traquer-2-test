import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from './exam-type.service';
import { ExamTypeDeleteDialogComponent } from './exam-type-delete-dialog.component';

@Component({
  selector: 'jhi-exam-type',
  templateUrl: './exam-type.component.html'
})
export class ExamTypeComponent implements OnInit, OnDestroy {
  examTypes?: IExamType[];
  eventSubscriber?: Subscription;

  constructor(protected examTypeService: ExamTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.examTypeService.query().subscribe((res: HttpResponse<IExamType[]>) => (this.examTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExamTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExamType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExamTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('examTypeListModification', () => this.loadAll());
  }

  delete(examType: IExamType): void {
    const modalRef = this.modalService.open(ExamTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examType = examType;
  }
}
