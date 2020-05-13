import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICloudInstance } from 'app/shared/model/cloud-instance.model';
import { CloudInstanceService } from './cloud-instance.service';
import { CloudInstanceDeleteDialogComponent } from './cloud-instance-delete-dialog.component';

@Component({
  selector: 'jhi-cloud-instance',
  templateUrl: './cloud-instance.component.html'
})
export class CloudInstanceComponent implements OnInit, OnDestroy {
  cloudInstances?: ICloudInstance[];
  eventSubscriber?: Subscription;

  constructor(
    protected cloudInstanceService: CloudInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.cloudInstanceService.query().subscribe((res: HttpResponse<ICloudInstance[]>) => (this.cloudInstances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCloudInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICloudInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCloudInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('cloudInstanceListModification', () => this.loadAll());
  }

  delete(cloudInstance: ICloudInstance): void {
    const modalRef = this.modalService.open(CloudInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cloudInstance = cloudInstance;
  }
}
