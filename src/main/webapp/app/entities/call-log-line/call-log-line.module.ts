import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CallLogLineComponent,
    CallLogLineDetailComponent,
    CallLogLineUpdateComponent,
    CallLogLineDeletePopupComponent,
    CallLogLineDeleteDialogComponent,
    callLogLineRoute,
    callLogLinePopupRoute
} from './';

const ENTITY_STATES = [...callLogLineRoute, ...callLogLinePopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CallLogLineComponent,
        CallLogLineDetailComponent,
        CallLogLineUpdateComponent,
        CallLogLineDeleteDialogComponent,
        CallLogLineDeletePopupComponent
    ],
    entryComponents: [CallLogLineComponent, CallLogLineUpdateComponent, CallLogLineDeleteDialogComponent, CallLogLineDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCallLogLineModule {}
