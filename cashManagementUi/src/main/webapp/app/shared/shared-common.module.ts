import { NgModule } from '@angular/core';

import { CashManagementUiSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';
import { PrettyPrintPipe } from './util/pretty-print.pipe';

@NgModule({
    imports: [CashManagementUiSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, PrettyPrintPipe],
    exports: [CashManagementUiSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent, PrettyPrintPipe]
})
export class CashManagementUiSharedCommonModule {}
