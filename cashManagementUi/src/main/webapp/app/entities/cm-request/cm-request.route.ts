import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from './cm-request.service';
import { CmRequestComponent } from './cm-request.component';
import { CmRequestDetailComponent } from './cm-request-detail.component';
import { CmRequestUpdateComponent } from './cm-request-update.component';
import { CmRequestDeletePopupComponent } from './cm-request-delete-dialog.component';
import { ICmRequest } from 'app/shared/model/cm-request.model';

@Injectable({ providedIn: 'root' })
export class CmRequestResolve implements Resolve<ICmRequest> {
    constructor(private service: CmRequestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CmRequest> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CmRequest>) => response.ok),
                map((cmRequest: HttpResponse<CmRequest>) => cmRequest.body)
            );
        }
        return of(new CmRequest());
    }
}

export const cmRequestRoute: Routes = [
    {
        path: 'cm-request',
        component: CmRequestComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'cashManagementUiApp.cmRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-request/:id/view',
        component: CmRequestDetailComponent,
        resolve: {
            cmRequest: CmRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-request/new',
        component: CmRequestUpdateComponent,
        resolve: {
            cmRequest: CmRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-request/:id/edit',
        component: CmRequestUpdateComponent,
        resolve: {
            cmRequest: CmRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmRequest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cmRequestPopupRoute: Routes = [
    {
        path: 'cm-request/:id/delete',
        component: CmRequestDeletePopupComponent,
        resolve: {
            cmRequest: CmRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmRequest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
