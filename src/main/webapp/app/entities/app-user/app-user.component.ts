import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAppUser } from 'app/shared/model/app-user.model';
import { AccountService } from 'app/core';
import { AppUserService } from './app-user.service';

@Component({
    selector: 'jhi-app-user',
    templateUrl: './app-user.component.html'
})
export class AppUserComponent implements OnInit, OnDestroy {
    appUsers: IAppUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected appUserService: AppUserService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.appUserService
            .query()
            .pipe(
                filter((res: HttpResponse<IAppUser[]>) => res.ok),
                map((res: HttpResponse<IAppUser[]>) => res.body)
            )
            .subscribe(
                (res: IAppUser[]) => {
                    this.appUsers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAppUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAppUser) {
        return item.id;
    }

    registerChangeInAppUsers() {
        this.eventSubscriber = this.eventManager.subscribe('appUserListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
