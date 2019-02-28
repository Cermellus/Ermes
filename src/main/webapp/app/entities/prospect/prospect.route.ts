import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prospect } from 'app/shared/model/prospect.model';
import { ProspectService } from './prospect.service';
import { ProspectComponent } from './prospect.component';
import { ProspectDetailComponent } from './prospect-detail.component';
import { ProspectUpdateComponent } from './prospect-update.component';
import { ProspectDeletePopupComponent } from './prospect-delete-dialog.component';
import { IProspect } from 'app/shared/model/prospect.model';

@Injectable({ providedIn: 'root' })
export class ProspectResolve implements Resolve<IProspect> {
    constructor(private service: ProspectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProspect> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Prospect>) => response.ok),
                map((prospect: HttpResponse<Prospect>) => prospect.body)
            );
        }
        return of(new Prospect());
    }
}

export const prospectRoute: Routes = [
    {
        path: '',
        component: ProspectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prospects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProspectDetailComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prospects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProspectUpdateComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prospects'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProspectUpdateComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prospects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prospectPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProspectDeletePopupComponent,
        resolve: {
            prospect: ProspectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prospects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
