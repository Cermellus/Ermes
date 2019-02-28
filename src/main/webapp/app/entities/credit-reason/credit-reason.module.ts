import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditReasonComponent,
    CreditReasonDetailComponent,
    CreditReasonUpdateComponent,
    CreditReasonDeletePopupComponent,
    CreditReasonDeleteDialogComponent,
    creditReasonRoute,
    creditReasonPopupRoute
} from './';

const ENTITY_STATES = [...creditReasonRoute, ...creditReasonPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditReasonComponent,
        CreditReasonDetailComponent,
        CreditReasonUpdateComponent,
        CreditReasonDeleteDialogComponent,
        CreditReasonDeletePopupComponent
    ],
    entryComponents: [
        CreditReasonComponent,
        CreditReasonUpdateComponent,
        CreditReasonDeleteDialogComponent,
        CreditReasonDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditReasonModule {}
