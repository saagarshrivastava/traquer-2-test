import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';
import { MajorIncidentSourceService } from './major-incident-source.service';
import { MajorIncidentSourceDeleteDialogComponent } from './major-incident-source-delete-dialog.component';

@Component({
  selector: 'jhi-major-incident-source',
  templateUrl: './major-incident-source.component.html'
})
export class MajorIncidentSourceComponent implements OnInit, OnDestroy {
  majorIncidentSources?: IMajorIncidentSource[];
  eventSubscriber?: Subscription;

  constructor(
    protected majorIncidentSourceService: MajorIncidentSourceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.majorIncidentSourceService
      .query()
      .subscribe((res: HttpResponse<IMajorIncidentSource[]>) => (this.majorIncidentSources = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMajorIncidentSources();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMajorIncidentSource): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMajorIncidentSources(): void {
    this.eventSubscriber = this.eventManager.subscribe('majorIncidentSourceListModification', () => this.loadAll());
  }

  delete(majorIncidentSource: IMajorIncidentSource): void {
    const modalRef = this.modalService.open(MajorIncidentSourceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.majorIncidentSource = majorIncidentSource;
  }
}
