import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CashManagementUiSharedModule } from 'app/shared';
import {
    CmMockComponent,
    CmMockDetailComponent,
    CmMockUpdateComponent,
    CmMockDeletePopupComponent,
    CmMockDeleteDialogComponent,
    cmMockRoute,
    cmMockPopupRoute
} from './';

const ENTITY_STATES = [...cmMockRoute, ...cmMockPopupRoute];

@NgModule({
    imports: [CashManagementUiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CmMockComponent, CmMockDetailComponent, CmMockUpdateComponent, CmMockDeleteDialogComponent, CmMockDeletePopupComponent],
    entryComponents: [CmMockComponent, CmMockUpdateComponent, CmMockDeleteDialogComponent, CmMockDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiCmMockModule {}
