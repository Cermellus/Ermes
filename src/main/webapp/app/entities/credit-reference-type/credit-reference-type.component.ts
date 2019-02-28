import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICreditReferenceType } from 'app/shared/model/credit-reference-type.model';
import { AccountService } from 'app/core';
import { CreditReferenceTypeService } from './credit-reference-type.service';

@Component({
    selector: 'jhi-credit-reference-type',
    templateUrl: './credit-reference-type.component.html'
})
export class CreditReferenceTypeComponent implements OnInit, OnDestroy {
    creditReferenceTypes: ICreditReferenceType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditReferenceTypeService: CreditReferenceTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditReferenceTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditReferenceType[]>) => res.ok),
                map((res: HttpResponse<ICreditReferenceType[]>) => res.body)
            )
            .subscribe(
                (res: ICreditReferenceType[]) => {
                    this.creditReferenceTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditReferenceTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditReferenceType) {
        return item.id;
    }

    registerChangeInCreditReferenceTypes() {
        this.eventSubscriber = this.eventManager.subscribe('creditReferenceTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
