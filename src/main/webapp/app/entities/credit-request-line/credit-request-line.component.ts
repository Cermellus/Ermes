import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';
import { AccountService } from 'app/core';
import { CreditRequestLineService } from './credit-request-line.service';

@Component({
    selector: 'jhi-credit-request-line',
    templateUrl: './credit-request-line.component.html'
})
export class CreditRequestLineComponent implements OnInit, OnDestroy {
    creditRequestLines: ICreditRequestLine[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditRequestLineService: CreditRequestLineService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditRequestLineService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditRequestLine[]>) => res.ok),
                map((res: HttpResponse<ICreditRequestLine[]>) => res.body)
            )
            .subscribe(
                (res: ICreditRequestLine[]) => {
                    this.creditRequestLines = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditRequestLines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditRequestLine) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInCreditRequestLines() {
        this.eventSubscriber = this.eventManager.subscribe('creditRequestLineListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
