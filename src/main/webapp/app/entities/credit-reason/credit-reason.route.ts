import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditReason } from 'app/shared/model/credit-reason.model';
import { CreditReasonService } from './credit-reason.service';
import { CreditReasonComponent } from './credit-reason.component';
import { CreditReasonDetailComponent } from './credit-reason-detail.component';
import { CreditReasonUpdateComponent } from './credit-reason-update.component';
import { CreditReasonDeletePopupComponent } from './credit-reason-delete-dialog.component';
import { ICreditReason } from 'app/shared/model/credit-reason.model';

@Injectable({ providedIn: 'root' })
export class CreditReasonResolve implements Resolve<ICreditReason> {
    constructor(private service: CreditReasonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditReason> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditReason>) => response.ok),
                map((creditReason: HttpResponse<CreditReason>) => creditReason.body)
            );
        }
        return of(new CreditReason());
    }
}

export const creditReasonRoute: Routes = [
    {
        path: '',
        component: CreditReasonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditReasonDetailComponent,
        resolve: {
            creditReason: CreditReasonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditReasonUpdateComponent,
        resolve: {
            creditReason: CreditReasonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditReasonUpdateComponent,
        resolve: {
            creditReason: CreditReasonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReasons'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditReasonPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditReasonDeletePopupComponent,
        resolve: {
            creditReason: CreditReasonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditReasons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
