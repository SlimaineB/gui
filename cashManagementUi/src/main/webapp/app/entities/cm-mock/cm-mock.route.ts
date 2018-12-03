import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CmMock } from 'app/shared/model/cm-mock.model';
import { CmMockService } from './cm-mock.service';
import { CmMockComponent } from './cm-mock.component';
import { CmMockDetailComponent } from './cm-mock-detail.component';
import { CmMockUpdateComponent } from './cm-mock-update.component';
import { CmMockDeletePopupComponent } from './cm-mock-delete-dialog.component';
import { ICmMock } from 'app/shared/model/cm-mock.model';

@Injectable({ providedIn: 'root' })
export class CmMockResolve implements Resolve<ICmMock> {
    constructor(private service: CmMockService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CmMock> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CmMock>) => response.ok),
                map((cmMock: HttpResponse<CmMock>) => cmMock.body)
            );
        }
        return of(new CmMock());
    }
}

export const cmMockRoute: Routes = [
    {
        path: 'cm-mock',
        component: CmMockComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cashManagementUiApp.cmMock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-mock/:id/view',
        component: CmMockDetailComponent,
        resolve: {
            cmMock: CmMockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmMock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-mock/new',
        component: CmMockUpdateComponent,
        resolve: {
            cmMock: CmMockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmMock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-mock/:id/edit',
        component: CmMockUpdateComponent,
        resolve: {
            cmMock: CmMockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmMock.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cmMockPopupRoute: Routes = [
    {
        path: 'cm-mock/:id/delete',
        component: CmMockDeletePopupComponent,
        resolve: {
            cmMock: CmMockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmMock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
