import { NgModule } from '@angular/core';

import { ErmesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ErmesSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ErmesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ErmesSharedCommonModule {}
