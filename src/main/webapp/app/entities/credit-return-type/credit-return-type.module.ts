import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditReturnTypeComponent,
    CreditReturnTypeDetailComponent,
    CreditReturnTypeUpdateComponent,
    CreditReturnTypeDeletePopupComponent,
    CreditReturnTypeDeleteDialogComponent,
    creditReturnTypeRoute,
    creditReturnTypePopupRoute
} from './';

const ENTITY_STATES = [...creditReturnTypeRoute, ...creditReturnTypePopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditReturnTypeComponent,
        CreditReturnTypeDetailComponent,
        CreditReturnTypeUpdateComponent,
        CreditReturnTypeDeleteDialogComponent,
        CreditReturnTypeDeletePopupComponent
    ],
    entryComponents: [
        CreditReturnTypeComponent,
        CreditReturnTypeUpdateComponent,
        CreditReturnTypeDeleteDialogComponent,
        CreditReturnTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditReturnTypeModule {}
