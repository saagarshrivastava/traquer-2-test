import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TraquerTestTestModule } from '../../../test.module';
import { IncidentComponent } from 'app/entities/incident/incident.component';
import { IncidentService } from 'app/entities/incident/incident.service';
import { Incident } from 'app/shared/model/incident.model';

describe('Component Tests', () => {
  describe('Incident Management Component', () => {
    let comp: IncidentComponent;
    let fixture: ComponentFixture<IncidentComponent>;
    let service: IncidentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TraquerTestTestModule],
        declarations: [IncidentComponent]
      })
        .overrideTemplate(IncidentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncidentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Incident(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incidents && comp.incidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
