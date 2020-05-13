import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExam } from 'app/shared/model/exam.model';
import { ExamService } from './exam.service';
import { ExamDeleteDialogComponent } from './exam-delete-dialog.component';

@Component({
  selector: 'jhi-exam',
  templateUrl: './exam.component.html'
})
export class ExamComponent implements OnInit, OnDestroy {
  exams?: IExam[];
  eventSubscriber?: Subscription;

  constructor(protected examService: ExamService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.examService.query().subscribe((res: HttpResponse<IExam[]>) => (this.exams = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExam): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExams(): void {
    this.eventSubscriber = this.eventManager.subscribe('examListModification', () => this.loadAll());
  }

  delete(exam: IExam): void {
    const modalRef = this.modalService.open(ExamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.exam = exam;
  }
}
