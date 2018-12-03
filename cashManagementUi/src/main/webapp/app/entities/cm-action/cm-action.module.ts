import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagementUiSharedModule } from 'app/shared';
import {
    CmActionComponent,
    CmActionDetailComponent,
    CmActionUpdateComponent,
    CmActionDeletePopupComponent,
    CmActionDeleteDialogComponent,
    cmActionRoute,
    cmActionPopupRoute
} from './';

const ENTITY_STATES = [...cmActionRoute, ...cmActionPopupRoute];

@NgModule({
    imports: [CashManagementUiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CmActionComponent,
        CmActionDetailComponent,
        CmActionUpdateComponent,
        CmActionDeleteDialogComponent,
        CmActionDeletePopupComponent
    ],
    entryComponents: [CmActionComponent, CmActionUpdateComponent, CmActionDeleteDialogComponent, CmActionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiCmActionModule {}
