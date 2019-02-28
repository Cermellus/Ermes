import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICallLog } from 'app/shared/model/call-log.model';
import { AccountService } from 'app/core';
import { CallLogService } from './call-log.service';

@Component({
    selector: 'jhi-call-log',
    templateUrl: './call-log.component.html'
})
export class CallLogComponent implements OnInit, OnDestroy {
    callLogs: ICallLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected callLogService: CallLogService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.callLogService
            .query()
            .pipe(
                filter((res: HttpResponse<ICallLog[]>) => res.ok),
                map((res: HttpResponse<ICallLog[]>) => res.body)
            )
            .subscribe(
                (res: ICallLog[]) => {
                    this.callLogs = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCallLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICallLog) {
        return item.id;
    }

    registerChangeInCallLogs() {
        this.eventSubscriber = this.eventManager.subscribe('callLogListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
