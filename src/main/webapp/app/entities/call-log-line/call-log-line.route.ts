import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CallLogLine } from 'app/shared/model/call-log-line.model';
import { CallLogLineService } from './call-log-line.service';
import { CallLogLineComponent } from './call-log-line.component';
import { CallLogLineDetailComponent } from './call-log-line-detail.component';
import { CallLogLineUpdateComponent } from './call-log-line-update.component';
import { CallLogLineDeletePopupComponent } from './call-log-line-delete-dialog.component';
import { ICallLogLine } from 'app/shared/model/call-log-line.model';

@Injectable({ providedIn: 'root' })
export class CallLogLineResolve implements Resolve<ICallLogLine> {
    constructor(private service: CallLogLineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICallLogLine> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CallLogLine>) => response.ok),
                map((callLogLine: HttpResponse<CallLogLine>) => callLogLine.body)
            );
        }
        return of(new CallLogLine());
    }
}

export const callLogLineRoute: Routes = [
    {
        path: '',
        component: CallLogLineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CallLogLineDetailComponent,
        resolve: {
            callLogLine: CallLogLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CallLogLineUpdateComponent,
        resolve: {
            callLogLine: CallLogLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogLines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CallLogLineUpdateComponent,
        resolve: {
            callLogLine: CallLogLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogLines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const callLogLinePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CallLogLineDeletePopupComponent,
        resolve: {
            callLogLine: CallLogLineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CallLogLines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
