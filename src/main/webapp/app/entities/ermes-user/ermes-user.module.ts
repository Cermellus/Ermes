import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    ErmesUserComponent,
    ErmesUserDetailComponent,
    ErmesUserUpdateComponent,
    ErmesUserDeletePopupComponent,
    ErmesUserDeleteDialogComponent,
    ermesUserRoute,
    ermesUserPopupRoute
} from './';

const ENTITY_STATES = [...ermesUserRoute, ...ermesUserPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ErmesUserComponent,
        ErmesUserDetailComponent,
        ErmesUserUpdateComponent,
        ErmesUserDeleteDialogComponent,
        ErmesUserDeletePopupComponent
    ],
    entryComponents: [ErmesUserComponent, ErmesUserUpdateComponent, ErmesUserDeleteDialogComponent, ErmesUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesErmesUserModule {}
