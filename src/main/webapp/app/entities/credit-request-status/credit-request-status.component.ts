import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';
import { AccountService } from 'app/core';
import { CreditRequestStatusService } from './credit-request-status.service';

@Component({
    selector: 'jhi-credit-request-status',
    templateUrl: './credit-request-status.component.html'
})
export class CreditRequestStatusComponent implements OnInit, OnDestroy {
    creditRequestStatuses: ICreditRequestStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditRequestStatusService: CreditRequestStatusService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditRequestStatusService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditRequestStatus[]>) => res.ok),
                map((res: HttpResponse<ICreditRequestStatus[]>) => res.body)
            )
            .subscribe(
                (res: ICreditRequestStatus[]) => {
                    this.creditRequestStatuses = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditRequestStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditRequestStatus) {
        return item.id;
    }

    registerChangeInCreditRequestStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('creditRequestStatusListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
