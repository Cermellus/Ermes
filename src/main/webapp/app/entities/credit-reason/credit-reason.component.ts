import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICreditReason } from 'app/shared/model/credit-reason.model';
import { AccountService } from 'app/core';
import { CreditReasonService } from './credit-reason.service';

@Component({
    selector: 'jhi-credit-reason',
    templateUrl: './credit-reason.component.html'
})
export class CreditReasonComponent implements OnInit, OnDestroy {
    creditReasons: ICreditReason[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditReasonService: CreditReasonService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditReasonService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditReason[]>) => res.ok),
                map((res: HttpResponse<ICreditReason[]>) => res.body)
            )
            .subscribe(
                (res: ICreditReason[]) => {
                    this.creditReasons = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditReasons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditReason) {
        return item.id;
    }

    registerChangeInCreditReasons() {
        this.eventSubscriber = this.eventManager.subscribe('creditReasonListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
