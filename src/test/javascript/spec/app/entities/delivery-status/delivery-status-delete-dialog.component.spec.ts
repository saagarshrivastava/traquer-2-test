import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TraquerTestTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { DeliveryStatusDeleteDialogComponent } from 'app/entities/delivery-status/delivery-status-delete-dialog.component';
import { DeliveryStatusService } from 'app/entities/delivery-status/delivery-status.service';

describe('Component Tests', () => {
  describe('DeliveryStatus Management Delete Component', () => {
    let comp: DeliveryStatusDeleteDialogComponent;
    let fixture: ComponentFixture<DeliveryStatusDeleteDialogComponent>;
    let service: DeliveryStatusService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TraquerTestTestModule],
        declarations: [DeliveryStatusDeleteDialogComponent]
      })
        .overrideTemplate(DeliveryStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeliveryStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryStatusService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
