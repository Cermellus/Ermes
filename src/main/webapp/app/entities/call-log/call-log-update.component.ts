import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICallLog } from 'app/shared/model/call-log.model';
import { CallLogService } from './call-log.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { IProspect } from 'app/shared/model/prospect.model';
import { ProspectService } from 'app/entities/prospect';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';

@Component({
    selector: 'jhi-call-log-update',
    templateUrl: './call-log-update.component.html'
})
export class CallLogUpdateComponent implements OnInit {
    callLog: ICallLog;
    isSaving: boolean;

    customers: ICustomer[];

    prospects: IProspect[];

    appusers: IAppUser[];
    callLogDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected callLogService: CallLogService,
        protected customerService: CustomerService,
        protected prospectService: ProspectService,
        protected appUserService: AppUserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ callLog }) => {
            this.callLog = callLog;
        });
        this.customerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomer[]>) => response.body)
            )
            .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.prospectService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProspect[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProspect[]>) => response.body)
            )
            .subscribe((res: IProspect[]) => (this.prospects = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.appUserService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAppUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAppUser[]>) => response.body)
            )
            .subscribe((res: IAppUser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.callLog.id !== undefined) {
            this.subscribeToSaveResponse(this.callLogService.update(this.callLog));
        } else {
            this.subscribeToSaveResponse(this.callLogService.create(this.callLog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICallLog>>) {
        result.subscribe((res: HttpResponse<ICallLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }

    trackProspectById(index: number, item: IProspect) {
        return item.id;
    }

    trackAppUserById(index: number, item: IAppUser) {
        return item.id;
    }
}
