import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProspect } from 'app/shared/model/prospect.model';
import { AccountService } from 'app/core';
import { ProspectService } from './prospect.service';

@Component({
    selector: 'jhi-prospect',
    templateUrl: './prospect.component.html'
})
export class ProspectComponent implements OnInit, OnDestroy {
    prospects: IProspect[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected prospectService: ProspectService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.prospectService
            .query()
            .pipe(
                filter((res: HttpResponse<IProspect[]>) => res.ok),
                map((res: HttpResponse<IProspect[]>) => res.body)
            )
            .subscribe(
                (res: IProspect[]) => {
                    this.prospects = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProspects();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProspect) {
        return item.id;
    }

    registerChangeInProspects() {
        this.eventSubscriber = this.eventManager.subscribe('prospectListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
