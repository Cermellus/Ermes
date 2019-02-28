import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CallLog } from 'app/shared/model/call-log.model';
import { CallLogService } from './call-log.service';
import { CallLogComponent } from './call-log.component';
import { CallLogDetailComponent } from './call-log-detail.component';
import { CallLogUpdateComponent } from './call-log-update.component';
import { CallLogDeletePopupComponent } from './call-log-delete-dialog.component';
import { ICallLog } from 'app/shared/model/call-log.model';

@Injectable({ providedIn: 'root' })
export class CallLogResolve implements Resolve<ICallLog> {
    constructor(private service: CallLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICallLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CallLog>) => response.ok),
                map((callLog: HttpResponse<CallLog>) => callLog.body)
            );
        }
        return of(new CallLog());
    }
}

export const callLogRoute: Routes = [
    {
        path: '',
        component: CallLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CallLogDetailComponent,
        resolve: {
            callLog: CallLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CallLogUpdateComponent,
        resolve: {
            callLog: CallLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CallLogUpdateComponent,
        resolve: {
            callLog: CallLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const callLogPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CallLogDeletePopupComponent,
        resolve: {
            callLog: CallLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
