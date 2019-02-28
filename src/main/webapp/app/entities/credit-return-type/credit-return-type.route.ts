import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditReturnType } from 'app/shared/model/credit-return-type.model';
import { CreditReturnTypeService } from './credit-return-type.service';
import { CreditReturnTypeComponent } from './credit-return-type.component';
import { CreditReturnTypeDetailComponent } from './credit-return-type-detail.component';
import { CreditReturnTypeUpdateComponent } from './credit-return-type-update.component';
import { CreditReturnTypeDeletePopupComponent } from './credit-return-type-delete-dialog.component';
import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';

@Injectable({ providedIn: 'root' })
export class CreditReturnTypeResolve implements Resolve<ICreditReturnType> {
    constructor(private service: CreditReturnTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditReturnType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditReturnType>) => response.ok),
                map((creditReturnType: HttpResponse<CreditReturnType>) => creditReturnType.body)
            );
        }
        return of(new CreditReturnType());
    }
}

export const creditReturnTypeRoute: Routes = [
    {
        path: '',
        component: CreditReturnTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReturnTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditReturnTypeDetailComponent,
        resolve: {
            creditReturnType: CreditReturnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReturnTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditReturnTypeUpdateComponent,
        resolve: {
            creditReturnType: CreditReturnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReturnTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditReturnTypeUpdateComponent,
        resolve: {
            creditReturnType: CreditReturnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReturnTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditReturnTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditReturnTypeDeletePopupComponent,
        resolve: {
            creditReturnType: CreditReturnTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReturnTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
