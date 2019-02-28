import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICreditReason } from 'app/shared/model/credit-reason.model';

@Component({
    selector: 'jhi-credit-reason-detail',
    templateUrl: './credit-reason-detail.component.html'
})
export class CreditReasonDetailComponent implements OnInit {
    creditReason: ICreditReason;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReason }) => {
            this.creditReason = creditReason;
        });
    }

    previousState() {
        window.history.back();
    }
}
