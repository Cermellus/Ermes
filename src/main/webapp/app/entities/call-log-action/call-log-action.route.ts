import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CallLogAction } from 'app/shared/model/call-log-action.model';
import { CallLogActionService } from './call-log-action.service';
import { CallLogActionComponent } from './call-log-action.component';
import { CallLogActionDetailComponent } from './call-log-action-detail.component';
import { CallLogActionUpdateComponent } from './call-log-action-update.component';
import { CallLogActionDeletePopupComponent } from './call-log-action-delete-dialog.component';
import { ICallLogAction } from 'app/shared/model/call-log-action.model';

@Injectable({ providedIn: 'root' })
export class CallLogActionResolve implements Resolve<ICallLogAction> {
    constructor(private service: CallLogActionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICallLogAction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CallLogAction>) => response.ok),
                map((callLogAction: HttpResponse<CallLogAction>) => callLogAction.body)
            );
        }
        return of(new CallLogAction());
    }
}

export const callLogActionRoute: Routes = [
    {
        path: '',
        component: CallLogActionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogActions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CallLogActionDetailComponent,
        resolve: {
            callLogAction: CallLogActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogActions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CallLogActionUpdateComponent,
        resolve: {
            callLogAction: CallLogActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogActions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CallLogActionUpdateComponent,
        resolve: {
            callLogAction: CallLogActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogActions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const callLogActionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CallLogActionDeletePopupComponent,
        resolve: {
            callLogAction: CallLogActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogActions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
