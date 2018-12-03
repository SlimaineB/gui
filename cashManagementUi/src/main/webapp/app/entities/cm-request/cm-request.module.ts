import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagementUiSharedModule } from 'app/shared';
import {
    CmRequestComponent,
    CmRequestDetailComponent,
    CmRequestUpdateComponent,
    CmRequestDeletePopupComponent,
    CmRequestDeleteDialogComponent,
    cmRequestRoute,
    cmRequestPopupRoute
} from './';

const ENTITY_STATES = [...cmRequestRoute, ...cmRequestPopupRoute];

@NgModule({
    imports: [CashManagementUiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CmRequestComponent,
        CmRequestDetailComponent,
        CmRequestUpdateComponent,
        CmRequestDeleteDialogComponent,
        CmRequestDeletePopupComponent
    ],
    entryComponents: [CmRequestComponent, CmRequestUpdateComponent, CmRequestDeleteDialogComponent, CmRequestDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiCmRequestModule {}
