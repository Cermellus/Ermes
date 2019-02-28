import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICallLog } from 'app/shared/model/call-log.model';

@Component({
    selector: 'jhi-call-log-detail',
    templateUrl: './call-log-detail.component.html'
})
export class CallLogDetailComponent implements OnInit {
    callLog: ICallLog;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLog }) => {
            this.callLog = callLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
