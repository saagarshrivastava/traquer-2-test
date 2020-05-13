import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { SubcategoryComponent } from './subcategory.component';
import { SubcategoryDetailComponent } from './subcategory-detail.component';
import { SubcategoryUpdateComponent } from './subcategory-update.component';
import { SubcategoryDeleteDialogComponent } from './subcategory-delete-dialog.component';
import { subcategoryRoute } from './subcategory.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(subcategoryRoute)],
  declarations: [SubcategoryComponent, SubcategoryDetailComponent, SubcategoryUpdateComponent, SubcategoryDeleteDialogComponent],
  entryComponents: [SubcategoryDeleteDialogComponent]
})
export class TraquerTestSubcategoryModule {}
