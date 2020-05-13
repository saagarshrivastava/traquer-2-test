import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TraquerTestSharedModule } from 'app/shared/shared.module';
import { CategoryComponent } from './category.component';
import { CategoryDetailComponent } from './category-detail.component';
import { CategoryUpdateComponent } from './category-update.component';
import { CategoryDeleteDialogComponent } from './category-delete-dialog.component';
import { categoryRoute } from './category.route';

@NgModule({
  imports: [TraquerTestSharedModule, RouterModule.forChild(categoryRoute)],
  declarations: [CategoryComponent, CategoryDetailComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent],
  entryComponents: [CategoryDeleteDialogComponent]
})
export class TraquerTestCategoryModule {}
