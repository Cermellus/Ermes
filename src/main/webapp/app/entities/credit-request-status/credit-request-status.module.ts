import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditRequestStatusComponent,
    CreditRequestStatusDetailComponent,
    CreditRequestStatusUpdateComponent,
    CreditRequestStatusDeletePopupComponent,
    CreditRequestStatusDeleteDialogComponent,
    creditRequestStatusRoute,
    creditRequestStatusPopupRoute
} from './';

const ENTITY_STATES = [...creditRequestStatusRoute, ...creditRequestStatusPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditRequestStatusComponent,
        CreditRequestStatusDetailComponent,
        CreditRequestStatusUpdateComponent,
        CreditRequestStatusDeleteDialogComponent,
        CreditRequestStatusDeletePopupComponent
    ],
    entryComponents: [
        CreditRequestStatusComponent,
        CreditRequestStatusUpdateComponent,
        CreditRequestStatusDeleteDialogComponent,
        CreditRequestStatusDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditRequestStatusModule {}
