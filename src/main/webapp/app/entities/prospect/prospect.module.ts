import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ErmesSharedModule } from 'app/shared';
import {
    ProspectComponent,
    ProspectDetailComponent,
    ProspectUpdateComponent,
    ProspectDeletePopupComponent,
    ProspectDeleteDialogComponent,
    prospectRoute,
    prospectPopupRoute
} from './';

const ENTITY_STATES = [...prospectRoute, ...prospectPopupRoute];

@NgModule({
    imports: [ErmesSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProspectComponent,
        ProspectDetailComponent,
        ProspectUpdateComponent,
        ProspectDeleteDialogComponent,
        ProspectDeletePopupComponent
    ],
    entryComponents: [ProspectComponent, ProspectUpdateComponent, ProspectDeleteDialogComponent, ProspectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ErmesProspectModule {}
