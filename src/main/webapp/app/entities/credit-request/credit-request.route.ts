import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditRequest } from 'app/shared/model/credit-request.model';
import { CreditRequestService } from './credit-request.service';
import { CreditRequestComponent } from './credit-request.component';
import { CreditRequestDetailComponent } from './credit-request-detail.component';
import { CreditRequestUpdateComponent } from './credit-request-update.component';
import { CreditRequestDeletePopupComponent } from './credit-request-delete-dialog.component';
import { ICreditRequest } from 'app/shared/model/credit-request.model';

@Injectable({ providedIn: 'root' })
export class CreditRequestResolve implements Resolve<ICreditRequest> {
    constructor(private service: CreditRequestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditRequest> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditRequest>) => response.ok),
                map((creditRequest: HttpResponse<CreditRequest>) => creditRequest.body)
            );
        }
        return of(new CreditRequest());
    }
}

export const creditRequestRoute: Routes = [
    {
        path: '',
        component: CreditRequestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditRequestDetailComponent,
        resolve: {
            creditRequest: CreditRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditRequestUpdateComponent,
        resolve: {
            creditRequest: CreditRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditRequestUpdateComponent,
        resolve: {
            creditRequest: CreditRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditRequestPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditRequestDeletePopupComponent,
        resolve: {
            creditRequest: CreditRequestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
