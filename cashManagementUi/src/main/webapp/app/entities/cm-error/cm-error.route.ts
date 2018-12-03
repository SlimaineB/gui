import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CmError } from 'app/shared/model/cm-error.model';
import { CmErrorService } from './cm-error.service';
import { CmErrorComponent } from './cm-error.component';
import { CmErrorDetailComponent } from './cm-error-detail.component';
import { CmErrorUpdateComponent } from './cm-error-update.component';
import { CmErrorDeletePopupComponent } from './cm-error-delete-dialog.component';
import { ICmError } from 'app/shared/model/cm-error.model';

@Injectable({ providedIn: 'root' })
export class CmErrorResolve implements Resolve<ICmError> {
    constructor(private service: CmErrorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CmError> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CmError>) => response.ok),
                map((cmError: HttpResponse<CmError>) => cmError.body)
            );
        }
        return of(new CmError());
    }
}

export const cmErrorRoute: Routes = [
    {
        path: 'cm-error',
        component: CmErrorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmError.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-error/:id/view',
        component: CmErrorDetailComponent,
        resolve: {
            cmError: CmErrorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmError.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-error/new',
        component: CmErrorUpdateComponent,
        resolve: {
            cmError: CmErrorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmError.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cm-error/:id/edit',
        component: CmErrorUpdateComponent,
        resolve: {
            cmError: CmErrorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmError.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cmErrorPopupRoute: Routes = [
    {
        path: 'cm-error/:id/delete',
        component: CmErrorDeletePopupComponent,
        resolve: {
            cmError: CmErrorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cashManagementUiApp.cmError.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
