import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CreditRequestLine } from 'app/shared/model/credit-request-line.model';
import { CreditRequestLineService } from './credit-request-line.service';
import { CreditRequestLineComponent } from './credit-request-line.component';
import { CreditRequestLineDetailComponent } from './credit-request-line-detail.component';
import { CreditRequestLineUpdateComponent } from './credit-request-line-update.component';
import { CreditRequestLineDeletePopupComponent } from './credit-request-line-delete-dialog.component';
import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';

@Injectable({ providedIn: 'root' })
export class CreditRequestLineResolve implements Resolve<ICreditRequestLine> {
    constructor(private service: CreditRequestLineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICreditRequestLine> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CreditRequestLine>) => response.ok),
                map((creditRequestLine: HttpResponse<CreditRequestLine>) => creditRequestLine.body)
            );
        }
        return of(new CreditRequestLine());
    }
}

export const creditRequestLineRoute: Routes = [
    {
        path: '',
        component: CreditRequestLineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CreditRequestLineDetailComponent,
        resolve: {
            creditRequestLine: CreditRequestLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CreditRequestLineUpdateComponent,
        resolve: {
            creditRequestLine: CreditRequestLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CreditRequestLineUpdateComponent,
        resolve: {
            creditRequestLine: CreditRequestLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestLines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditRequestLinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CreditRequestLineDeletePopupComponent,
        resolve: {
            creditRequestLine: CreditRequestLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditRequestLines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
