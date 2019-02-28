import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { AccountService } from 'app/core';
import { SalesPersonService } from './sales-person.service';

@Component({
    selector: 'jhi-sales-person',
    templateUrl: './sales-person.component.html'
})
export class SalesPersonComponent implements OnInit, OnDestroy {
    salesPeople: ISalesPerson[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected salesPersonService: SalesPersonService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.salesPersonService
            .query()
            .pipe(
                filter((res: HttpResponse<ISalesPerson[]>) => res.ok),
                map((res: HttpResponse<ISalesPerson[]>) => res.body)
            )
            .subscribe(
                (res: ISalesPerson[]) => {
                    this.salesPeople = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSalesPeople();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISalesPerson) {
        return item.id;
    }

    registerChangeInSalesPeople() {
        this.eventSubscriber = this.eventManager.subscribe('salesPersonListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
