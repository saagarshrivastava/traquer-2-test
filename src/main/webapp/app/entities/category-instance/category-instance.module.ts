import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { CategoryInstanceComponent } from './category-instance.component';
import { CategoryInstanceDetailComponent } from './category-instance-detail.component';
import { CategoryInstanceUpdateComponent } from './category-instance-update.component';
import { CategoryInstanceDeleteDialogComponent } from './category-instance-delete-dialog.component';
import { categoryInstanceRoute } from './category-instance.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(categoryInstanceRoute)],
  declarations: [
    CategoryInstanceComponent,
    CategoryInstanceDetailComponent,
    CategoryInstanceUpdateComponent,
    CategoryInstanceDeleteDialogComponent
  ],
  entryComponents: [CategoryInstanceDeleteDialogComponent]
})
export class TraquerTestCategoryInstanceModule {}
