import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IProspect } from 'app/shared/model/prospect.model';
import { ProspectService } from 'app/entities/prospect';
import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { SalesPersonService } from 'app/entities/sales-person';

@Component({
    selector: 'jhi-customer-update',
    templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
    customer: ICustomer;
    isSaving: boolean;

    prospectids: IProspect[];

    salespeople: ISalesPerson[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected customerService: CustomerService,
        protected prospectService: ProspectService,
        protected salesPersonService: SalesPersonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customer }) => {
            this.customer = customer;
        });
        this.prospectService
            .query({ filter: 'customer-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IProspect[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProspect[]>) => response.body)
            )
            .subscribe(
                (res: IProspect[]) => {
                    if (!this.customer.prospectIdId) {
                        this.prospectids = res;
                    } else {
                        this.prospectService
                            .find(this.customer.prospectIdId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IProspect>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IProspect>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IProspect) => (this.prospectids = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.salesPersonService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISalesPerson[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISalesPerson[]>) => response.body)
            )
            .subscribe((res: ISalesPerson[]) => (this.salespeople = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customer.id !== undefined) {
            this.subscribeToSaveResponse(this.customerService.update(this.customer));
        } else {
            this.subscribeToSaveResponse(this.customerService.create(this.customer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
        result.subscribe((res: HttpResponse<ICustomer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProspectById(index: number, item: IProspect) {
        return item.id;
    }

    trackSalesPersonById(index: number, item: ISalesPerson) {
        return item.id;
    }
}
