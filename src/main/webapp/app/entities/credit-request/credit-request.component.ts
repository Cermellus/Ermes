import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICreditRequest } from 'app/shared/model/credit-request.model';
import { AccountService } from 'app/core';
import { CreditRequestService } from './credit-request.service';

@Component({
    selector: 'jhi-credit-request',
    templateUrl: './credit-request.component.html'
})
export class CreditRequestComponent implements OnInit, OnDestroy {
    creditRequests: ICreditRequest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditRequestService: CreditRequestService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditRequestService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditRequest[]>) => res.ok),
                map((res: HttpResponse<ICreditRequest[]>) => res.body)
            )
            .subscribe(
                (res: ICreditRequest[]) => {
                    this.creditRequests = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditRequest) {
        return item.id;
    }

    registerChangeInCreditRequests() {
        this.eventSubscriber = this.eventManager.subscribe('creditRequestListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
