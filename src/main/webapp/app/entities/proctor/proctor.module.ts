import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { ProctorComponent } from './proctor.component';
import { ProctorDetailComponent } from './proctor-detail.component';
import { ProctorUpdateComponent } from './proctor-update.component';
import { ProctorDeleteDialogComponent } from './proctor-delete-dialog.component';
import { proctorRoute } from './proctor.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(proctorRoute)],
  declarations: [ProctorComponent, ProctorDetailComponent, ProctorUpdateComponent, ProctorDeleteDialogComponent],
  entryComponents: [ProctorDeleteDialogComponent]
})
export class TraquerTestProctorModule {}
