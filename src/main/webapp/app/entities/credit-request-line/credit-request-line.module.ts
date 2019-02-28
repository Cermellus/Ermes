import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    CreditRequestLineComponent,
    CreditRequestLineDetailComponent,
    CreditRequestLineUpdateComponent,
    CreditRequestLineDeletePopupComponent,
    CreditRequestLineDeleteDialogComponent,
    creditRequestLineRoute,
    creditRequestLinePopupRoute
} from './';

const ENTITY_STATES = [...creditRequestLineRoute, ...creditRequestLinePopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditRequestLineComponent,
        CreditRequestLineDetailComponent,
        CreditRequestLineUpdateComponent,
        CreditRequestLineDeleteDialogComponent,
        CreditRequestLineDeletePopupComponent
    ],
    entryComponents: [
        CreditRequestLineComponent,
        CreditRequestLineUpdateComponent,
        CreditRequestLineDeleteDialogComponent,
        CreditRequestLineDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesCreditRequestLineModule {}
