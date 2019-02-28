import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditRequestStatus } from 'app/shared/model/credit-request-status.model';
import { CreditRequestStatusService } from './credit-request-status.service';
import { CreditRequestStatusComponent } from './credit-request-status.component';
import { CreditRequestStatusDetailComponent } from './credit-request-status-detail.component';
import { CreditRequestStatusUpdateComponent } from './credit-request-status-update.component';
import { CreditRequestStatusDeletePopupComponent } from './credit-request-status-delete-dialog.component';
import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';

@Injectable({ providedIn: 'root' })
export class CreditRequestStatusResolve implements Resolve<ICreditRequestStatus> {
    constructor(private service: CreditRequestStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditRequestStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditRequestStatus>) => response.ok),
                map((creditRequestStatus: HttpResponse<CreditRequestStatus>) => creditRequestStatus.body)
            );
        }
        return of(new CreditRequestStatus());
    }
}

export const creditRequestStatusRoute: Routes = [
    {
        path: '',
        component: CreditRequestStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditRequestStatusDetailComponent,
        resolve: {
            creditRequestStatus: CreditRequestStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditRequestStatusUpdateComponent,
        resolve: {
            creditRequestStatus: CreditRequestStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditRequestStatusUpdateComponent,
        resolve: {
            creditRequestStatus: CreditRequestStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditRequestStatusPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditRequestStatusDeletePopupComponent,
        resolve: {
            creditRequestStatus: CreditRequestStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
