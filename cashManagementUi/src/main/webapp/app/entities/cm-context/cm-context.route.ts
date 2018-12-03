import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CmContext } from 'app/shared/model/cm-context.model';
import { CmContextService } from './cm-context.service';
import { CmContextComponent } from './cm-context.component';
import { CmContextDetailComponent } from './cm-context-detail.component';
import { CmContextUpdateComponent } from './cm-context-update.component';
import { CmContextDeletePopupComponent } from './cm-context-delete-dialog.component';
import { ICmContext } from 'app/shared/model/cm-context.model';

@Injectable({ providedIn: 'root' })
export class CmContextResolve implements Resolve<ICmContext> {
    constructor(private service: CmContextService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CmContext> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CmContext>) => response.ok),
                map((cmContext: HttpResponse<CmContext>) => cmContext.body)
            );
        }
        return of(new CmContext());
    }
}

export const cmContextRoute: Routes = [
    {
        path: 'cm-context',
        component: CmContextComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmContext.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-context/:id/view',
        component: CmContextDetailComponent,
        resolve: {
            cmContext: CmContextResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmContext.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-context/new',
        component: CmContextUpdateComponent,
        resolve: {
            cmContext: CmContextResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmContext.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-context/:id/edit',
        component: CmContextUpdateComponent,
        resolve: {
            cmContext: CmContextResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmContext.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cmContextPopupRoute: Routes = [
    {
        path: 'cm-context/:id/delete',
        component: CmContextDeletePopupComponent,
        resolve: {
            cmContext: CmContextResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmContext.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
