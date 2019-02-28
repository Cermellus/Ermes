import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICallLogLine } from 'app/shared/model/call-log-line.model';

@Component({
    selector: 'jhi-call-log-line-detail',
    templateUrl: './call-log-line-detail.component.html'
})
export class CallLogLineDetailComponent implements OnInit {
    callLogLine: ICallLogLine;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ callLogLine }) => {
            this.callLogLine = callLogLine;
        });
    }

    previousState() {
        window.history.back();
    }
}
