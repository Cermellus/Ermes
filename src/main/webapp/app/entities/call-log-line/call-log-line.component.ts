import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICallLogLine } from 'app/shared/model/call-log-line.model';
import { AccountService } from 'app/core';
import { CallLogLineService } from './call-log-line.service';

@Component({
    selector: 'jhi-call-log-line',
    templateUrl: './call-log-line.component.html'
})
export class CallLogLineComponent implements OnInit, OnDestroy {
    callLogLines: ICallLogLine[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected callLogLineService: CallLogLineService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.callLogLineService
            .query()
            .pipe(
                filter((res: HttpResponse<ICallLogLine[]>) => res.ok),
                map((res: HttpResponse<ICallLogLine[]>) => res.body)
            )
            .subscribe(
                (res: ICallLogLine[]) => {
                    this.callLogLines = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCallLogLines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICallLogLine) {
        return item.id;
    }

    registerChangeInCallLogLines() {
        this.eventSubscriber = this.eventManager.subscribe('callLogLineListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
