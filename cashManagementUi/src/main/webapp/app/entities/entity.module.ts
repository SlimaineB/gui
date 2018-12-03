import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CashManagementUiCmActionModule } from './cm-action/cm-action.module';
import { CashManagementUiCmErrorModule } from './cm-error/cm-error.module';
import { CashManagementUiCmContextModule } from './cm-context/cm-context.module';
import { CashManagementUiCmRequestModule } from './cm-request/cm-request.module';
import { CashManagementUiCmMockModule } from './cm-mock/cm-mock.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CashManagementUiCmActionModule,
        CashManagementUiCmErrorModule,
        CashManagementUiCmContextModule,
        CashManagementUiCmRequestModule,
        CashManagementUiCmMockModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CashManagementUiEntityModule {}
