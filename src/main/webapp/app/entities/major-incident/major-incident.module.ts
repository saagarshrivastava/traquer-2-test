import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { MajorIncidentComponent } from './major-incident.component';
import { MajorIncidentDetailComponent } from './major-incident-detail.component';
import { MajorIncidentUpdateComponent } from './major-incident-update.component';
import { MajorIncidentDeleteDialogComponent } from './major-incident-delete-dialog.component';
import { majorIncidentRoute } from './major-incident.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(majorIncidentRoute)],
  declarations: [MajorIncidentComponent, MajorIncidentDetailComponent, MajorIncidentUpdateComponent, MajorIncidentDeleteDialogComponent],
  entryComponents: [MajorIncidentDeleteDialogComponent]
})
export class TraquerTestMajorIncidentModule {}
