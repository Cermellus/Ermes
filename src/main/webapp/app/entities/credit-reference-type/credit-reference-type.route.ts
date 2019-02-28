import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditReferenceType } from 'app/shared/model/credit-reference-type.model';
import { CreditReferenceTypeService } from './credit-reference-type.service';
import { CreditReferenceTypeComponent } from './credit-reference-type.component';
import { CreditReferenceTypeDetailComponent } from './credit-reference-type-detail.component';
import { CreditReferenceTypeUpdateComponent } from './credit-reference-type-update.component';
import { CreditReferenceTypeDeletePopupComponent } from './credit-reference-type-delete-dialog.component';
import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';

@Injectable({ providedIn: 'root' })
export class CreditReferenceTypeResolve implements Resolve<ICreditReferenceType> {
    constructor(private service: CreditReferenceTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditReferenceType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditReferenceType>) => response.ok),
                map((creditReferenceType: HttpResponse<CreditReferenceType>) => creditReferenceType.body)
            );
        }
        return of(new CreditReferenceType());
    }
}

export const creditReferenceTypeRoute: Routes = [
    {
        path: '',
        component: CreditReferenceTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReferenceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditReferenceTypeDetailComponent,
        resolve: {
            creditReferenceType: CreditReferenceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReferenceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditReferenceTypeUpdateComponent,
        resolve: {
            creditReferenceType: CreditReferenceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReferenceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditReferenceTypeUpdateComponent,
        resolve: {
            creditReferenceType: CreditReferenceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReferenceTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditReferenceTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditReferenceTypeDeletePopupComponent,
        resolve: {
            creditReferenceType: CreditReferenceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReferenceTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
