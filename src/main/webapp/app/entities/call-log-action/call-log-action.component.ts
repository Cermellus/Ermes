import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICallLogAction } from 'app/shared/model/call-log-action.model';
import { AccountService } from 'app/core';
import { CallLogActionService } from './call-log-action.service';

@Component({
    selector: 'jhi-call-log-action',
    templateUrl: './call-log-action.component.html'
})
export class CallLogActionComponent implements OnInit, OnDestroy {
    callLogActions: ICallLogAction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected callLogActionService: CallLogActionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.callLogActionService
            .query()
            .pipe(
                filter((res: HttpResponse<ICallLogAction[]>) => res.ok),
                map((res: HttpResponse<ICallLogAction[]>) => res.body)
            )
            .subscribe(
                (res: ICallLogAction[]) => {
                    this.callLogActions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCallLogActions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICallLogAction) {
        return item.id;
    }

    registerChangeInCallLogActions() {
        this.eventSubscriber = this.eventManager.subscribe('callLogActionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
