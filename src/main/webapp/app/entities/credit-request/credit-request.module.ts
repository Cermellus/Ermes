import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditRequestComponent,
    CreditRequestDetailComponent,
    CreditRequestUpdateComponent,
    CreditRequestDeletePopupComponent,
    CreditRequestDeleteDialogComponent,
    creditRequestRoute,
    creditRequestPopupRoute
} from './';

const ENTITY_STATES = [...creditRequestRoute, ...creditRequestPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditRequestComponent,
        CreditRequestDetailComponent,
        CreditRequestUpdateComponent,
        CreditRequestDeleteDialogComponent,
        CreditRequestDeletePopupComponent
    ],
    entryComponents: [
        CreditRequestComponent,
        CreditRequestUpdateComponent,
        CreditRequestDeleteDialogComponent,
        CreditRequestDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditRequestModule {}
