import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ErmesUser } from 'app/shared/model/ermes-user.model';
import { ErmesUserService } from './ermes-user.service';
import { ErmesUserComponent } from './ermes-user.component';
import { ErmesUserDetailComponent } from './ermes-user-detail.component';
import { ErmesUserUpdateComponent } from './ermes-user-update.component';
import { ErmesUserDeletePopupComponent } from './ermes-user-delete-dialog.component';
import { IErmesUser } from 'app/shared/model/ermes-user.model';

@Injectable({ providedIn: 'root' })
export class ErmesUserResolve implements Resolve<IErmesUser> {
    constructor(private service: ErmesUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IErmesUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ErmesUser>) => response.ok),
                map((ermesUser: HttpResponse<ErmesUser>) => ermesUser.body)
            );
        }
        return of(new ErmesUser());
    }
}

export const ermesUserRoute: Routes = [
    {
        path: '',
        component: ErmesUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ErmesUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ErmesUserDetailComponent,
        resolve: {
            ermesUser: ErmesUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ErmesUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ErmesUserUpdateComponent,
        resolve: {
            ermesUser: ErmesUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ErmesUsers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ErmesUserUpdateComponent,
        resolve: {
            ermesUser: ErmesUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ErmesUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ermesUserPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ErmesUserDeletePopupComponent,
        resolve: {
            ermesUser: ErmesUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ErmesUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
