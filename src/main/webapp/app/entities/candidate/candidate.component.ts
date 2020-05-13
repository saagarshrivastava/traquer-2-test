import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';
import { CandidateDeleteDialogComponent } from './candidate-delete-dialog.component';

@Component({
  selector: 'jhi-candidate',
  templateUrl: './candidate.component.html'
})
export class CandidateComponent implements OnInit, OnDestroy {
  candidates?: ICandidate[];
  eventSubscriber?: Subscription;

  constructor(protected candidateService: CandidateService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.candidateService.query().subscribe((res: HttpResponse<ICandidate[]>) => (this.candidates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCandidates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICandidate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCandidates(): void {
    this.eventSubscriber = this.eventManager.subscribe('candidateListModification', () => this.loadAll());
  }

  delete(candidate: ICandidate): void {
    const modalRef = this.modalService.open(CandidateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidate = candidate;
  }
}
