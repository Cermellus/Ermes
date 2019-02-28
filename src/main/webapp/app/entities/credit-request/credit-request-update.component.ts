import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICreditRequest } from 'app/shared/model/credit-request.model';
import { CreditRequestService } from './credit-request.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';
import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';
import { CreditReferenceTypeService } from 'app/entities/credit-reference-type';
import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';
import { CreditRequestStatusService } from 'app/entities/credit-request-status';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user';

@Component({
    selector: 'jhi-credit-request-update',
    templateUrl: './credit-request-update.component.html'
})
export class CreditRequestUpdateComponent implements OnInit {
    creditRequest: ICreditRequest;
    isSaving: boolean;

    customers: ICustomer[];

    creditreferencetypes: ICreditReferenceType[];

    creditrequeststatuses: ICreditRequestStatus[];

    appusers: IAppUser[];
    creditRequestDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected creditRequestService: CreditRequestService,
        protected customerService: CustomerService,
        protected creditReferenceTypeService: CreditReferenceTypeService,
        protected creditRequestStatusService: CreditRequestStatusService,
        protected appUserService: AppUserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditRequest }) => {
            this.creditRequest = creditRequest;
        });
        this.customerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomer[]>) => response.body)
            )
            .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.creditReferenceTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditReferenceType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditReferenceType[]>) => response.body)
            )
            .subscribe(
                (res: ICreditReferenceType[]) => (this.creditreferencetypes = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.creditRequestStatusService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditRequestStatus[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditRequestStatus[]>) => response.body)
            )
            .subscribe(
                (res: ICreditRequestStatus[]) => (this.creditrequeststatuses = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.creditRequest.id !== undefined) {
            this.subscribeToSaveResponse(this.creditRequestService.update(this.creditRequest));
        } else {
            this.subscribeToSaveResponse(this.creditRequestService.create(this.creditRequest));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditRequest>>) {
        result.subscribe((res: HttpResponse<ICreditRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCreditReferenceTypeById(index: number, item: ICreditReferenceType) {
        return item.id;
    }

    trackCreditRequestStatusById(index: number, item: ICreditRequestStatus) {
        return item.id;
    }

    trackAppUserById(index: number, item: IAppUser) {
        return item.id;
    }
}
