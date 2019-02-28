import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CallLogActionComponent,
    CallLogActionDetailComponent,
    CallLogActionUpdateComponent,
    CallLogActionDeletePopupComponent,
    CallLogActionDeleteDialogComponent,
    callLogActionRoute,
    callLogActionPopupRoute
} from './';

const ENTITY_STATES = [...callLogActionRoute, ...callLogActionPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CallLogActionComponent,
        CallLogActionDetailComponent,
        CallLogActionUpdateComponent,
        CallLogActionDeleteDialogComponent,
        CallLogActionDeletePopupComponent
    ],
    entryComponents: [
        CallLogActionComponent,
        CallLogActionUpdateComponent,
        CallLogActionDeleteDialogComponent,
        CallLogActionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCallLogActionModule {}
