import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamBackend, ExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from './exam-backend.service';
import { ExamBackendComponent } from './exam-backend.component';
import { ExamBackendDetailComponent } from './exam-backend-detail.component';
import { ExamBackendUpdateComponent } from './exam-backend-update.component';

@Injectable({ providedIn: 'root' })
export class ExamBackendResolve implements Resolve<IExamBackend> {
  constructor(private service: ExamBackendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamBackend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examBackend: HttpResponse<ExamBackend>) => {
          if (examBackend.body) {
            return of(examBackend.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamBackend());
  }
}

export const examBackendRoute: Routes = [
  {
    path: '',
    component: ExamBackendComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'traquerTestApp.examBackend.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExamBackendDetailComponent,
    resolve: {
      examBackend: ExamBackendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'traquerTestApp.examBackend.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExamBackendUpdateComponent,
    resolve: {
      examBackend: ExamBackendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'traquerTestApp.examBackend.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExamBackendUpdateComponent,
    resolve: {
      examBackend: ExamBackendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'traquerTestApp.examBackend.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
