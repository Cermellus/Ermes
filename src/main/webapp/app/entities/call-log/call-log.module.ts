import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CallLogComponent,
    CallLogDetailComponent,
    CallLogUpdateComponent,
    CallLogDeletePopupComponent,
    CallLogDeleteDialogComponent,
    callLogRoute,
    callLogPopupRoute
} from './';

const ENTITY_STATES = [...callLogRoute, ...callLogPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CallLogComponent,
        CallLogDetailComponent,
        CallLogUpdateComponent,
        CallLogDeleteDialogComponent,
        CallLogDeletePopupComponent
    ],
    entryComponents: [CallLogComponent, CallLogUpdateComponent, CallLogDeleteDialogComponent, CallLogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCallLogModule {}
