import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IErmesUser } from 'app/shared/model/ermes-user.model';
import { AccountService } from 'app/core';
import { ErmesUserService } from './ermes-user.service';

@Component({
    selector: 'jhi-ermes-user',
    templateUrl: './ermes-user.component.html'
})
export class ErmesUserComponent implements OnInit, OnDestroy {
    ermesUsers: IErmesUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected ermesUserService: ErmesUserService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.ermesUserService
            .query()
            .pipe(
                filter((res: HttpResponse<IErmesUser[]>) => res.ok),
                map((res: HttpResponse<IErmesUser[]>) => res.body)
            )
            .subscribe(
                (res: IErmesUser[]) => {
                    this.ermesUsers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInErmesUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IErmesUser) {
        return item.id;
    }

    registerChangeInErmesUsers() {
        this.eventSubscriber = this.eventManager.subscribe('ermesUserListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
