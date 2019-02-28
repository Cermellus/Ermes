import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICreditReturnType } from 'app/shared/model/credit-return-type.model';
import { AccountService } from 'app/core';
import { CreditReturnTypeService } from './credit-return-type.service';

@Component({
    selector: 'jhi-credit-return-type',
    templateUrl: './credit-return-type.component.html'
})
export class CreditReturnTypeComponent implements OnInit, OnDestroy {
    creditReturnTypes: ICreditReturnType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected creditReturnTypeService: CreditReturnTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.creditReturnTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICreditReturnType[]>) => res.ok),
                map((res: HttpResponse<ICreditReturnType[]>) => res.body)
            )
            .subscribe(
                (res: ICreditReturnType[]) => {
                    this.creditReturnTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditReturnTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICreditReturnType) {
        return item.id;
    }

    registerChangeInCreditReturnTypes() {
        this.eventSubscriber = this.eventManager.subscribe('creditReturnTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
