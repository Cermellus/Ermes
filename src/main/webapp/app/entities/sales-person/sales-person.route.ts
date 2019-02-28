import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalesPerson } from 'app/shared/model/sales-person.model';
import { SalesPersonService } from './sales-person.service';
import { SalesPersonComponent } from './sales-person.component';
import { SalesPersonDetailComponent } from './sales-person-detail.component';
import { SalesPersonUpdateComponent } from './sales-person-update.component';
import { SalesPersonDeletePopupComponent } from './sales-person-delete-dialog.component';
import { ISalesPerson } from 'app/shared/model/sales-person.model';

@Injectable({ providedIn: 'root' })
export class SalesPersonResolve implements Resolve<ISalesPerson> {
    constructor(private service: SalesPersonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalesPerson> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalesPerson>) => response.ok),
                map((salesPerson: HttpResponse<SalesPerson>) => salesPerson.body)
            );
        }
        return of(new SalesPerson());
    }
}

export const salesPersonRoute: Routes = [
    {
        path: '',
        component: SalesPersonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalesPersonDetailComponent,
        resolve: {
            salesPerson: SalesPersonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalesPersonUpdateComponent,
        resolve: {
            salesPerson: SalesPersonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalesPersonUpdateComponent,
        resolve: {
            salesPerson: SalesPersonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salesPersonPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalesPersonDeletePopupComponent,
        resolve: {
            salesPerson: SalesPersonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalesPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
