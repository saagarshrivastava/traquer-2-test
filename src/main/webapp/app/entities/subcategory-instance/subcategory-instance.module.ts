import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { SubcategoryInstanceComponent } from './subcategory-instance.component';
import { SubcategoryInstanceDetailComponent } from './subcategory-instance-detail.component';
import { SubcategoryInstanceUpdateComponent } from './subcategory-instance-update.component';
import { SubcategoryInstanceDeleteDialogComponent } from './subcategory-instance-delete-dialog.component';
import { subcategoryInstanceRoute } from './subcategory-instance.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(subcategoryInstanceRoute)],
  declarations: [
    SubcategoryInstanceComponent,
    SubcategoryInstanceDetailComponent,
    SubcategoryInstanceUpdateComponent,
    SubcategoryInstanceDeleteDialogComponent
  ],
  entryComponents: [SubcategoryInstanceDeleteDialogComponent]
})
export class TraquerTestSubcategoryInstanceModule {}
