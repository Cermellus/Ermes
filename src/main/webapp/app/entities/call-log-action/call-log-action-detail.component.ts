import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICallLogAction } from 'app/shared/model/call-log-action.model';

@Component({
    selector: 'jhi-call-log-action-detail',
    templateUrl: './call-log-action-detail.component.html'
})
export class CallLogActionDetailComponent implements OnInit {
    callLogAction: ICallLogAction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLogAction }) => {
            this.callLogAction = callLogAction;
        });
    }

    previousState() {
        window.history.back();
    }
}
