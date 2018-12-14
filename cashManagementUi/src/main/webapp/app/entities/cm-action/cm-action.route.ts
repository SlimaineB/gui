import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CmAction } from 'app/shared/model/cm-action.model';
import { CmActionService } from './cm-action.service';
import { CmActionComponent } from './cm-action.component';
import { CmActionDetailComponent } from './cm-action-detail.component';
import { CmActionUpdateComponent } from './cm-action-update.component';
import { CmActionDeletePopupComponent } from './cm-action-delete-dialog.component';
import { ICmAction } from 'app/shared/model/cm-action.model';

@Injectable({ providedIn: 'root' })
export class CmActionResolve implements Resolve<ICmAction> {
    constructor(private service: CmActionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CmAction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CmAction>) => response.ok),
                map((cmAction: HttpResponse<CmAction>) => cmAction.body)
            );
        }
        return of(new CmAction());
    }
}

@Injectable({ providedIn: 'root' })
export class CmActionListResolve implements Resolve<HttpResponse<ICmAction[]>> {
    constructor(private service: CmActionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HttpResponse<CmAction[]>> {
        const requestId = { 'requestId.equals': route.params['id'] ? route.params['id'] : null };

        if (requestId) {
            return this.service.query(requestId).pipe(
                filter((response: HttpResponse<CmAction[]>) => response.ok),
                map((cmActions: HttpResponse<CmAction[]>) => cmActions)
            );
        }
        return of(new HttpResponse<CmAction[]>());
    }
}

export const cmActionRoute: Routes = [
    {
        path: 'cm-action',
        component: CmActionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-action/:id/view',
        component: CmActionDetailComponent,
        resolve: {
            cmAction: CmActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-action/new',
        component: CmActionUpdateComponent,
        resolve: {
            cmAction: CmActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-action/:id/edit',
        component: CmActionUpdateComponent,
        resolve: {
            cmAction: CmActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-action/:id/list',
        component: CmActionComponent,
        resolve: {
            requestActions: CmActionListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cmActionPopupRoute: Routes = [
    {
        path: 'cm-action/:id/delete',
        component: CmActionDeletePopupComponent,
        resolve: {
            cmAction: CmActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmAction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
