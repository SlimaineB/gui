import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagementUiSharedModule } from 'app/shared';
import {
    CmErrorComponent,
    CmErrorDetailComponent,
    CmErrorUpdateComponent,
    CmErrorDeletePopupComponent,
    CmErrorDeleteDialogComponent,
    cmErrorRoute,
    cmErrorPopupRoute
} from './';

const ENTITY_STATES = [...cmErrorRoute, ...cmErrorPopupRoute];

@NgModule({
    imports: [CashManagementUiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CmErrorComponent,
        CmErrorDetailComponent,
        CmErrorUpdateComponent,
        CmErrorDeleteDialogComponent,
        CmErrorDeletePopupComponent
    ],
    entryComponents: [CmErrorComponent, CmErrorUpdateComponent, CmErrorDeleteDialogComponent, CmErrorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiCmErrorModule {}
