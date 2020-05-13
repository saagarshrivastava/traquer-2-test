import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.TraquerTestSessionModule)
      },
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.TraquerTestScheduleModule)
      },
      {
        path: 'candidate',
        loadChildren: () => import('./candidate/candidate.module').then(m => m.TraquerTestCandidateModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.TraquerTestLocationModule)
      },
      {
        path: 'proctoring-instance',
        loadChildren: () => import('./proctoring-instance/proctoring-instance.module').then(m => m.TraquerTestProctoringInstanceModule)
      },
      {
        path: 'session-breaks',
        loadChildren: () => import('./session-breaks/session-breaks.module').then(m => m.TraquerTestSessionBreaksModule)
      },
      {
        path: 'incident',
        loadChildren: () => import('./incident/incident.module').then(m => m.TraquerTestIncidentModule)
      },
      {
        path: 'category-instance',
        loadChildren: () => import('./category-instance/category-instance.module').then(m => m.TraquerTestCategoryInstanceModule)
      },
      {
        path: 'subcategory-instance',
        loadChildren: () => import('./subcategory-instance/subcategory-instance.module').then(m => m.TraquerTestSubcategoryInstanceModule)
      },
      {
        path: 'major-incident',
        loadChildren: () => import('./major-incident/major-incident.module').then(m => m.TraquerTestMajorIncidentModule)
      },
      {
        path: 'major-incident-source',
        loadChildren: () => import('./major-incident-source/major-incident-source.module').then(m => m.TraquerTestMajorIncidentSourceModule)
      },
      {
        path: 'proctor',
        loadChildren: () => import('./proctor/proctor.module').then(m => m.TraquerTestProctorModule)
      },
      {
        path: 'support-instance',
        loadChildren: () => import('./support-instance/support-instance.module').then(m => m.TraquerTestSupportInstanceModule)
      },
      {
        path: 'support-person',
        loadChildren: () => import('./support-person/support-person.module').then(m => m.TraquerTestSupportPersonModule)
      },
      {
        path: 'delivery-type',
        loadChildren: () => import('./delivery-type/delivery-type.module').then(m => m.TraquerTestDeliveryTypeModule)
      },
      {
        path: 'exam-type',
        loadChildren: () => import('./exam-type/exam-type.module').then(m => m.TraquerTestExamTypeModule)
      },
      {
        path: 'delivery-status',
        loadChildren: () => import('./delivery-status/delivery-status.module').then(m => m.TraquerTestDeliveryStatusModule)
      },
      {
        path: 'failure-stage',
        loadChildren: () => import('./failure-stage/failure-stage.module').then(m => m.TraquerTestFailureStageModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.TraquerTestCategoryModule)
      },
      {
        path: 'subcategory',
        loadChildren: () => import('./subcategory/subcategory.module').then(m => m.TraquerTestSubcategoryModule)
      },
      {
        path: 'exam',
        loadChildren: () => import('./exam/exam.module').then(m => m.TraquerTestExamModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.TraquerTestRegionModule)
      },
      {
        path: 'exam-backend',
        loadChildren: () => import('./exam-backend/exam-backend.module').then(m => m.TraquerTestExamBackendModule)
      },
      {
        path: 'offer-type',
        loadChildren: () => import('./offer-type/offer-type.module').then(m => m.TraquerTestOfferTypeModule)
      },
      {
        path: 'cloud-instance',
        loadChildren: () => import('./cloud-instance/cloud-instance.module').then(m => m.TraquerTestCloudInstanceModule)
      },
      {
        path: 'cloud-region',
        loadChildren: () => import('./cloud-region/cloud-region.module').then(m => m.TraquerTestCloudRegionModule)
      },
      {
        path: 'offer',
        loadChildren: () => import('./offer/offer.module').then(m => m.TraquerTestOfferModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TraquerTestEntityModule {}
