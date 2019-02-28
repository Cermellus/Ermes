import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditReferenceTypeComponent,
    CreditReferenceTypeDetailComponent,
    CreditReferenceTypeUpdateComponent,
    CreditReferenceTypeDeletePopupComponent,
    CreditReferenceTypeDeleteDialogComponent,
    creditReferenceTypeRoute,
    creditReferenceTypePopupRoute
} from './';

const ENTITY_STATES = [...creditReferenceTypeRoute, ...creditReferenceTypePopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditReferenceTypeComponent,
        CreditReferenceTypeDetailComponent,
        CreditReferenceTypeUpdateComponent,
        CreditReferenceTypeDeleteDialogComponent,
        CreditReferenceTypeDeletePopupComponent
    ],
    entryComponents: [
        CreditReferenceTypeComponent,
        CreditReferenceTypeUpdateComponent,
        CreditReferenceTypeDeleteDialogComponent,
        CreditReferenceTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditReferenceTypeModule {}
