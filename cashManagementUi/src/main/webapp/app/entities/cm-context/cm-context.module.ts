import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagementUiSharedModule } from 'app/shared';
import {
    CmContextComponent,
    CmContextDetailComponent,
    CmContextUpdateComponent,
    CmContextDeletePopupComponent,
    CmContextDeleteDialogComponent,
    cmContextRoute,
    cmContextPopupRoute
} from './';

const ENTITY_STATES = [...cmContextRoute, ...cmContextPopupRoute];

@NgModule({
    imports: [CashManagementUiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CmContextComponent,
        CmContextDetailComponent,
        CmContextUpdateComponent,
        CmContextDeleteDialogComponent,
        CmContextDeletePopupComponent
    ],
    entryComponents: [CmContextComponent, CmContextUpdateComponent, CmContextDeleteDialogComponent, CmContextDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiCmContextModule {}
